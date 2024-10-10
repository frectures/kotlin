# Coroutines

- 1 thread can execute 1000s of coroutines, but only one at a time
  - When one coroutine *suspends*, another coroutine can *resume* in that thread
- A coroutine *may* suspend in one thread and resume in another thread
  - only `Dispatchers.Main` confines coroutines to the same (UI) thread
- Suspension is implemented via `return` statement (*stackless coroutines*)
  - unlike Goroutines or Java 21 Virtual Threads (*stackful coroutines*)

## Agenda

1. Generators
2. Responsive GUIs
3. Desugaring `suspend`

## Generators

### Fibonacci

```python
def fibonacci():
    a = 0
    b = 1

    while True:
        yield a
        a += b

        yield b
        b += a


for x in fibonacci():
    print(x)


generator = fibonacci()
try:
    while True:
        print(next(generator))
except StopIteration:
```

```javascript
function* fibonacci() {
    let a = 0n;
    let b = BigInt(1);

    while (true) {
        yield a;
        a += b;

        yield b;
        b += a;
    }
}

for (const x of fibonacci()) {
    console.log(x);
}

const generator = fibonacci();
let value, done;
while ({value, done} = generator.next(), !done) {
    console.log(value);
}
```

```csharp
using System;
using System.Collections.Generic;
using System.Numerics;

IEnumerable<BigInteger> fibonacci()
{
    BigInteger a = 0;
    BigInteger b = new BigInteger(1);

    while (true)
    {
        yield return a;
        a += b;

        yield return b;
        b += a;
    }
}


foreach (var x in fibonacci())
{
    Console.WriteLine(x);
}


IEnumerator<BigInteger> generator = fibonacci().GetEnumerator();
while (generator.MoveNext())
{
    Console.WriteLine(generator.Current);
}
```

Popular compilation strategy for generators:
- return generator object from generator function
- refactor local variables into fields
- wrap function body with `switch` statement (*state machine*)
- remember resumption label before suspending

```cpp
#include <iostream>

class Fibonacci
{
    enum { L, M, N } label = L;

    int a, b;

public:

    bool hasNext() const
    {
        return true;
    }

    int next()
    {
        switch (label)
        {
case L:     a = 0;
            b = 1;

            while (true)
            {
label = M;      return a;
case M:         a += b;

label = N;      return b;
case N:         b += a;
            }
        }
    }
};

int main()
{
    auto generator = Fibonacci();
    while (generator.hasNext())
    {
        std::cout << generator.next() << "\n";
    }
}
```

Kotlin generators are a standard library feature, not a language feature:

```kotlin
package generators

import java.math.BigInteger

fun fibonacci(): Sequence<BigInteger> = sequence {
    var a = 0.toBigInteger()
    var b = 1.toBigInteger()

    while (true) {
        yield(a)
        a += b

        yield(b)
        b += a
    }
}

fun main() {
    for (x in fibonacci()) {
        println(x)
    }

    val generator = fibonacci().iterator()
    while (generator.hasNext()) {
        println(generator.next())
    }
}
```

The underlying language feature are `suspend` functions:

```kotlin
public fun <T> sequence(block: suspend SequenceScope<T>.() -> Unit): Sequence<T>
//                             ^^^^^^^                  ^^^^^^^^^^

/**
 * A sequence that returns values through its iterator.
 * The values are evaluated lazily, and
 * the sequence is potentially infinite.
 */
public interface Sequence<T> {

    public operator fun iterator(): Iterator<T>
}

/**
 * The scope for yielding values of a [Sequence] or an [Iterator],
 * provides [yield] and [yieldAll] suspension functions.
 */
public abstract class SequenceScope<T> {
    /**
     * Yields a value to the [Iterator] being built
     * and suspends until the next value is requested.
     */
    public abstract suspend fun yield(value: T)

    public abstract suspend fun yieldAll(iterator: Iterator<T>)
    public          suspend fun yieldAll(elements: Iterable<T>)
    public          suspend fun yieldAll(sequence: Sequence<T>)
}
```

low level generator with coroutine primitives in `generators.FiLowNacci`

### Chessboard positions

```java
package generators;

class ChessboardPositionsGenerator {
    public record Position(char col, int row) {
        @Override
        public String toString() {
            return col + "" + row;
        }
    }

    public static Iterable<Position> generateChessboardPositions() {
        return new Iterable<Position>() {
            public Iterator<Position> iterator() {
                return new Iterator<Position>() {
                    private char col = 'A';
                    private int row = 1;

                    public boolean hasNext() {
                        return col <= 'H';
                    }

                    public Position next() {
                        if (!hasNext()) throw new NoSuchElementException();

                        Position result = new Position(col, row);
                        ++row;
                        if (row > 8) {
                            row = 1;
                            ++col;
                        }
                        return result;
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        for (var position : generateChessboardPositions()) {
            System.out.println(position);
        }
    }
}
```

```kotlin
package generators

data class Position(val col: Char, val row: Int) {
    override fun toString() = "$col$row"
}

fun generateChessboardPositions(): Sequence<Position> = sequence {
    for (col in 'A'..'H') {
        for (row in 1..8) {
            yield(Position(col, row))
        }
    }
}

fun main() {
    for (position in generateChessboardPositions()) {
        println(position)
    }
}
```

### Lazy map

```kotlin
fun <T, R> Iterable<T>.eagerMap(f: (T) -> R): List<R> {
    val result = ArrayList<R>()
    for (x in this) {
        result.add(f(x))
    }
    return result
}

fun <T, R> Iterable<T>.lazyMap(f: (T) -> R): Iterable<R> = object : Iterable<R> {
    override fun iterator(): Iterator<R> = object : Iterator<R> {
        private val source = this@lazyMap.iterator()

        override fun hasNext(): Boolean {
            return source.hasNext()
        }

        override fun next(): R {
            return f(source.next())
        }
    }
}

fun <T, R> Sequence<T>.lazyMap(f: (T) -> R): Sequence<R> = sequence {
    for (x in this@lazyMap) {
        yield(f(x))
    }
}
```

### Lazy filter

```kotlin
fun <T> Iterable<T>.eagerFilter(good: (T) -> Boolean): List<T> {
    val result = ArrayList<T>()
    for (x in this) {
        if (good(x)) {
            result.add(x)
        }
    }
    return result
}

fun <T> Iterable<T>.lazyFilter(good: (T) -> Boolean): Iterable<T> {
    TODO("filter is much harder than map") 
}

fun <T> Sequence<T>.lazyFilter(good: (T) -> Boolean): Sequence<T> = sequence {
    for (x in this@lazyFilter) {
        if (good(x)) {
            yield(x)
        }
    }
}
```

## Responsive GUIs

> *Java Concurrency in Practice 9.1 Why are GUIs single-threaded?*
>
> There have been many attempts to write multithreaded GUI frameworks, but because of persistent problems with race conditions and deadlock, they all eventually arrived at the single-threaded event queue model in which a dedicated thread fetches events off a queue and dispatches them to application-defined event handlers.
>
> Single-threaded GUI frameworks achieve thread safety via thread confinement; all GUI objects, including visual components and data models, are accessed exclusively from the event thread.
> Of course, this just pushes some of the thread safety burden back onto the application developer, who must make sure these objects are properly confined.

### Swing

blocks the UI thread for 3 seconds, countdown invisible:

```kotlin
package countdown

fun main() {
    val button = JButton("click me")
    with(button) {
        font = font.deriveFont(384f)

        addActionListener {
            isEnabled = false
            for (i in 3 downTo 1) {
                text = "$i"
                Thread.sleep(1000) // blocks
            }
            text = "click me"
            isEnabled = true
            // return; let Swing paint
        }

    }
    with(JFrame()) {
        add(button)
        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}
```

replace normal loop with elusive corecursion:

```kotlin
        addActionListener {
            isEnabled = false
            var i = 3

            fun tick() {
                if (i >= 1) {
                    text = "$i"
                    // does NOT block
                    val timer = javax.swing.Timer(1000) {
                        --i
                        tick() // corecursion
                    }
                    timer.isRepeats = false
                    timer.start()
                } else {
                    text = "click me"
                    isEnabled = true
                }
                // return; let Swing paint
            }
            tick() // start clock
        }
```

coroutines can delay inside normal loops:

```kotlin
        addActionListener {
            val scope = CoroutineScope(Dispatchers.Main)
            /**
             * Launches a new coroutine without blocking the current thread
             **/
            scope.launch {
                isEnabled = false
                for (i in 3 downTo 1) {
                    text = "$i"
                    /**
                     * Delays coroutine for a given time without blocking a thread
                     */
                    delay(1000) // let Swing paint
                }
                text = "click me"
                isEnabled = true
            }
        }
```

manual delay with coroutine primitives:

```kotlin
                    /**
                     * Obtains the current continuation instance inside suspend functions
                     * and suspends the currently running coroutine.
                     *
                     * In this function both [Continuation.resume] and [Continuation.resumeWithException] can be used
                     * either synchronously in the same stack-frame where the suspension function is run
                     * or asynchronously later in the same thread
                     * or from a different thread of execution.
                     */
                    suspendCoroutine<Unit> { continuation ->
                        val timer = javax.swing.Timer(1000) {
                            /**
                             * Resumes the execution of the corresponding coroutine
                             * passing [value] as the return value of the last suspension point.
                             */
                            continuation.resume(Unit)
                        }
                        timer.isRepeats = false
                        timer.start()
                    }// let Swing paint
```

more practical coroutine example in `package digest`

## Desugaring `suspend`

Every `suspend` function has an additional, hidden `continuation` parameter:

```kotlin
suspend fun f(before: String, after: String): Int {
    println(before)
    suspendCoroutine<Unit> { continuation ->
        // ...
    }
    println(after)
    return 42
}
```

```kotlin
fun desugared(before: String, after: String, continuation: Continuation): Any {
    when (continuation.label) {                                        // Int | CoroutineSingletons.COROUTINE_SUSPENDED
        0 -> {
            println(before)
            // ...
            continuation.label = 1
            return CoroutineSingletons.COROUTINE_SUSPENDED
        }
        1 -> {
            println(after)
            return 42
        }
        else -> throw IllegalStateException()
    }
}
```

Comparison of call stack before suspension and after resumption:

```kotlin
import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Dispatchers.Main)
    scope.launch {
        f()
    }
    Thread.sleep(2000)
}

suspend fun f() {
    g()
}

suspend fun g() {
    h()
}

suspend fun h() {
    Thread.dumpStack()
    delay(1000)
    Thread.dumpStack()
}
```

<table>
<tr>
<th>call stack before suspension</th>
<th>call stack after resumption</th>
</tr>
<tr>
<td>
  
```
HelloKt.h
HelloKt.g
HelloKt.f
HelloKt$main$1.invokeSuspend
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
BaseContinuationImpl.resumeWith
DispatchedTask.run


       package kotlin.coroutines
       package kotlinx.coroutines


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

java.awt.event.InvocationEvent.dispatch
...
java.awt.EventDispatchThread.run
```

</td>
<td>

```
HelloKt.h


HelloKt$h$1.invokeSuspend
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
BaseContinuationImpl.resumeWith
DispatchedTaskKt.resume
DispatchedTaskKt.dispatch
CancellableContinuationImpl.dispatchResume
CancellableContinuationImpl.resumeImpl
CancellableContinuationImpl.resumeImpl$default
CancellableContinuationImpl.resumeUndispatched
SwingDispatcher.scheduleResumeAfterDelay$lambda-1
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javax.swing.Timer.fireActionPerformed
java.awt.event.InvocationEvent.dispatch
...
java.awt.EventDispatchThread.run
```

</td>
</tr>
</table>


- [KotlinConf 2017 - Deep Dive into Coroutines on JVM by Roman Elizarov](https://www.youtube.com/watch?v=YrrUCSi72E8)
- https://github.com/Kotlin/KEEP/blob/master/proposals/coroutines.md#implementation-details
  - "internal classes and code generation strategies are subject to change at any time"
