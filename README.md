# Kotlin

![](img/kotlin.png)

## Testimonials

### [Egineering at Meta](https://twitter.com/fb_engineering/status/1584643794351583233)

> - Meta has adopted Kotlin as its **new primary language** for Android development
> - Meta is migrating its Android codebase from Java to Kotlin
> - over 10 million lines of Kotlin code

### [Adopting Kotlin at Google](https://www.youtube.com/watch?v=hXfGybzWaiA)

> - Kotlin is now the **recommended programming language**
>   - not only for Android programming
>   - but also for server-side JVM usage at Google
> - over 8 million lines of Kotlin code

## Kotlin in a nutshell

- “Java after spring cleaning”
  - “Java without the boilerplate”
- Killer features:
  - Null safety
  - Extension functions
  - Properties
- Compilation targets:
  - JVM 8+ bytecode (excellent Kotlin⟷Java interop)
  - [JavaScript](https://kotlinlang.org/docs/js-overview.html)
  - [WebAssembly](https://kotlinlang.org/docs/wasm-overview.html)
  - [Native](https://kotlinlang.org/docs/native-overview.html) (Windows, macOS, iOS, Linux, Android)

## Birth

![](img/jetbrains.png)
![](img/idea.png)
![](img/breslav.jpg)

```text
                                                                                    1.1    1.3       1.5    1.8
Java                            Scala                         Kotlin            1.0 |  1.2 |      1.4| 1.6  | 1.9
|                               |                             |                 |   |  |   |      |  | | 1.7| |
+---+---+---+---|---+---+---+---+---|---+---+---+---+---|---+---+---+---+---|---+---+---+---+---|---+---+---+---
96             00   |              05                  10         |        15          |       20
                |   IntelliJ IDEA                                 |          |         |
                |                                                 Karel      skorbut   Karel
                JetBrains                                        (Scala)     (Kotlin) (Kotlin)
```

## Hello World

```kotlin
fun main(args: Array<String>): Unit {
    println("Hello Kotlin!");
}
```

- Dedicated `fun` keyword for functions
- Top-level functions (`main`, `println`)
- Classic `name: type` declaration syntax
- No special syntax for arrays
  - `args` array can be omitted
- `Unit` is Kotlin-speak for `void`
  - Return type `Unit` can be omitted
- Semicolons can be omitted

```kotlin
fun main() {
    println("Hello Kotlin!")
}
```

> **Exercise**
> 1. Download [IntelliJ IDEA **Community Edition**](https://www.jetbrains.com/idea/download) 👇 **Scroll Down** 👇
> 2. Import the *Hello World* project
>    - File > New > Project from Version Control...
>    - URL: https://github.com/frectures/kotlin.git
>    - Clone
> 3. If the Project View is hidden, press Alt+1
> 4. Open `src/main/kotlin/Kotlin.kt`
> 5. Start the `main` function via the green triangle beside it
> 6. Modify the program to print something else

## Basic types

![](img/basic.svg)

### What problem does `Nothing` solve?

```java
public static void TODO() {
    throw new NotImplementedYet();
}

public static String interviewQuestion(String[] strings) {
    TODO();
}// missing return statement
```

- `void` means “returns normally, without a result”
  - same as `Unit` in Kotlin
- `Nothing` means “always throws an exception”:

```kotlin
fun TODO(): Nothing {
    throw NotImplementedError()
}

fun interviewQuestion(strings: Array<String>): String {
    TODO()
}
```

### Boxing

- `Int` is usually `int`, unless boxing necessitates `Integer`:

```kotlin
fun sumNumbers(numbers: List<Int>): Int {
                       // Integer   int
    var result = 0
             // int
    for (x in numbers) {
     // int
        result += x
    }
    return result
}

fun main() {
    println(sumNumbers(listOf(2, 3, 5, 7)))
    // Summing numbers is already implemented in the standard library though:
    println(listOf(2, 3, 5, 7).sum())
}
```

### Type inference

- `var` infers the type of the variable from its initializer:

```kotlin
var a: Int = 1

var b      = 2
// Int <<<< Int
```

- Type inference is *not* dynamic typing:

```kotlin
var c
// This variable must either have a type annotation or be initialized


var theAnswerToLife = 42

    theAnswerToLife = "nope"
//    Required: Int   Found: String
```

## Control structure expressions

### `if` expression

```kotlin
fun digitOrNumber(x: Int): String {
    if (x in 0..9) {
        return "digit"
    } else {
        return "number"
    }
}


fun digitOrNumber(x: Int): String {
    return if (x in 0..9) "digit" else "number"
}


fun digitOrNumber(x: Int): String = if (x in 0..9) "digit" else "number"


fun digitOrNumber(x: Int) = if (x in 0..9) "digit" else "number"
```

### `when` expression

```kotlin
fun averageMonthLength(month: Int): Double = when (month) {
    2                     -> 28.2425
    4, 6, 9, 11           -> 30.0
    1, 3, 5, 7, 8, 10, 12 -> 31.0
    else                  -> throw IllegalArgumentException("illegal month $month")
}

fun count(x: Any): Int = when (x) {
    is Array<*>      -> x.size
    is String        -> x.length
    is Collection<*> -> x.size
    else             -> 0
}

fun signum(x: Int): Int = when {
    x<0  -> -1
    x>0  -> +1
    else ->  0
}
```

> **Exercise**
> 1. Implement a function `gcd` which computes the greatest common divisor of two integers

## Default and Named arguments

```java
public class Joiner {
    public static String join(Iterable<String> strings, String delimiter, String prefix, String suffix) {
        // ...
    }

    public static void main(String[] args) {
        var fruits = List.of("apple", "banana", "cherry", "date", "elderberry");

        String a = join(fruits, ", ", "[", "]");
        String b = join(fruits, ", ");
        String c = join(fruits);
    }

    public static String join(Iterable<String> strings, String delimiter) {
        return join(strings, delimiter, "", "");
    }

    public static String join(Iterable<String> strings) {
        return join(strings, "");
    }
}
```

```kotlin
fun join(strings: Iterable<String>, delimiter: String = "", prefix: String = "", suffix: String = ""): String {
    // ...
}

fun main() {
    val fruits = listOf("apple", "banana", "cherry", "date", "elderberry")

    val a: String = join(fruits, ", ", "[", "]")
    val b: String = join(fruits, ", ")
    val c: String = join(fruits)

    val d: String = join(fruits, delimiter = ", ", prefix = "[", suffix = "]")
    val e: String = join(fruits, prefix = "[", delimiter = ", ", suffix = "]")
    val f: String = join(fruits, prefix = "\t")
}
```

- Default arguments are evaluated inside the called function
  - arbitrary expressions
  - not hardwired into call sites
- Unnamed arguments are forbidden after the first named argument

## `Any` is the new `Object`

- Every class without an explicit parent inherits from `Any`:
  - `class Child`
  - `class Child : Any()`

```kotlin
package kotlin

public open class Any {

    public open fun equals(other: Any): Boolean

    public open fun hashCode(): Int

    public open fun toString(): String
}
```

- `open class`es are inheritable
- `open fun`ctions are overridable

### [Joshua Bloch – Design and document for inheritance or else prohibit it](https://blogs.oracle.com/javamagazine/post/java-inheritance-design-document)

```java
class CountingList<E> extends ArrayList<E> {
    private int elementsAdded;
    
    @Override
    public boolean add(E e) {
        elementsAdded += 1;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        elementsAdded += c.size();
        // Does super.addAll call this.add internally?
        return super.addAll(c);
    }
}
```

> - Designing a class for inheritance is hard work
> - You must document all its self-use patterns, and once you've documented them,
> you must commit to them for the life of the class
> - If you fail to do this, subclasses may become dependent on implementation details of the superclass
> and may break if the implementation of the superclass changes
> - Unless you know there is a real need for subclasses,
> **you are probably better off prohibiting inheritance** by declaring your class `final`

### https://kotlinlang.org/docs/all-open-plugin.html

> - Kotlin has classes and their members `final` by default
>   - which makes it inconvenient to use frameworks and libraries such as Spring AOP that require classes to be `open`
> - The `all-open` compiler plugin adapts Kotlin to the requirements of those frameworks
>   - and makes **classes annotated with a specific annotation and their members open** without the explicit `open` keyword
>   - e.g. `@Transactional` classes

### Can you spot the bug?

```java
public static void login() { 
    Scanner scanner = new Scanner(System.in);
    String password;
    do {
        System.out.print("Password? ");
        password = scanner.nextLine();
    } while (password != "java");
    System.out.println("Welcome to the system!");
}
```

![](img/bug.jpg)

### Sane equality

```kotlin
fun login() {
    val scanner = Scanner(System.`in`)
    do {
        print("Password? ")
        val password = scanner.nextLine()
    } while (password != "kotlin")
    println("Welcome to the system!")
}
```

- Kotlin has no `new` keyword for constructor calls
  - Constructor calls look like function calls
  - Human disambiguation by first letter case
- Clashing keywords like `in` must be escaped with backticks
  - Backticks also enable readable test names like `square root should fail for negative inputs`
- `do` scope extends to `while` condition
  - i.e. `password` still accessible for comparison
- `==` and `!=` on references are null-safe `equals` comparisons
- `===` and `!==` compare the references themselves

```kotlin
println("hello".uppercase() ==  "HELLO")  // true
println("hello".uppercase() === "HELLO") // false
```

## Data classes

```java
record Name(String forename, String surename) {
    Name {
        if (forename.isBlank()) throw new IllegalArgumentException();
        if (surename.isBlank()) throw new IllegalArgumentException();
    }

    // auto-generated: equals, hashCode, toString
}
```

```kotlin
data class Name(val forename: String, val surename: String) {
    init {
        require(forename.isNotBlank())
        require(surename.isNotBlank())
    }

    // auto-generated: equals, hashCode, toString, copy, component1, component2
}
```

> **Exercise**
> 1. Implement a data class `Address` with fields of your choice
> 2. Populate a list of addresses
> 3. Print the addresses to the console

## Null safety

```kotlin
fun mustPassString(s: String) {
    // ...
}

fun canPassString(s: String?) {
    // ...
}

fun main() {
    mustPassString("hello")
    canPassString("world")

    mustPassString(null) // Null can not be a value of a non-null type String
    canPassString(null) // okay
}
```

### `NullPointerException` is very rare in Kotlin

```kotlin
fun mustPassString(s: String) {
    val length: Int = s.length
} //                   ^ okay

fun canPassString(s: String?) {
    val length: Int = s.length
    //                 ^ Only safe (?.) or non-null asserted (!!.) calls
    //                   are allowed on nullable receiver of type String?

    if (s != null) {
        val length: Int = s.length
    } //                  ^ Smart cast to kotlin.String

    val lengthOrNull: Int? = s?.length
    val lengthOrZero: Int  = s?.length ?: 0
}
```

### In vivo

```kotlin
fun largestDownload(): File {
    return File(System.getProperty("user.home") + File.separator + "Downloads")
        .listFiles()                // null if Downloads folder does not exist
        ?.maxByOrNull(File::length) // null if Downloads folder is empty
        ?: throw IOException("no Downloads")
}
```

```kotlin
class VirtualMachine {

    // ...

    var stack: Stack? = null     // empty stack

    private fun pop(): Int {
        val stack = this.stack!! // stack is never null when pop() is called,
                                 // but the compiler cannot know that
        this.stack = stack.tail
        return stack.head
    }

    // ...
}
```

### Java Interoperability

- `String!` denotes unknown nullability for Kotlin→Java interop:

![](img/null.svg)

- `public` functions check `null` parameters for Java→Kotlin interop:

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
private fun privateFunction(s: String) {
    // ...
}

fun publicFunction(s: String) {

    // ...
}
```

</td>
<td>

```java
private static void privateFunction(String s) {
    // ...
}

public static void publicFunction(String s) {
    Intrinsics.checkNotNullParameter(s);
    // ...
}
```

</td>
</tr>
</table>

> **Exercise**
> 1. Add a nullable field `remark` to the `Address` class
> 2. Print all addresses *with* remarks before all addresses *without* remarks

### `copy` data objects

```kotlin
val jamesGosling = Name("James", "Gosling")

val jamesBond = jamesGosling.copy(surename = "Bond")
```

- The `copy` method has default arguments for all fields:

```kotlin
fun copy(forename: String = this.forename, surename: String = this.surename) = Name(forename, surename)
```

> **Exercise**
> 1. Implement a `withoutRemark(): Address` method, using the `copy` method

## Extension functions

```kotlin
fun String.isAscii(): Boolean {
    for (ch in this) {
        if (ch > '\u007f') return false
    }
    return true
}

fun main() {
    println("Kaesebroetchen/Muesli".isAscii()) // true
    println("Käsebrötchen/Müsli".isAscii())   // false
}
```

- Extension functions are compiled to static helper methods with an additional `$receiver` parameter
- Inside an extension function, only the public interface of the `$receiver` is available

> **Exercise**
> 1. Implement an extension function `String.isPalindrome()`
> 2. Implement an extension function such that `listOf(2, 3, 5, 7).product()` returns 210

## Properties

### (Data classes)

```kotlin
data class Name(val forename: String, val surename: String)
```

### Enumerations

```kotlin
enum class Month(val averageDays: Double) {
    JAN(31.0),
    FEB(28.2425),
    MAR(31.0),
    APR(30.0),
    MAY(31.0),
    JUN(30.0),
    JUL(31.0),
    AUG(31.0),
    SEP(30.0),
    OCT(31.0),
    NOV(30.0),
    DEC(31.0);
}
```

### Data Transfer Objects

```kotlin
class PersonDto(var forename: String, var surename: String)
```

### Dependency Injection

```kotlin
class MyService(
    private val someOtherService: SomeOtherService,
    private val yetAnotherService: YetAnotherService,
) {
    // ...
}
```

### In vivo

```kotlin
class VirtualMachine(
    private val program: List<Instruction>,
    private val worldRef: WorldRef,
) {
    val world: World
        get() = worldRef.world

    var pc: Int = ENTRY_POINT
        private set(value) {
            assert(value in ENTRY_POINT..program.lastIndex) { "invalid pc $value" }
            field = value
        }

    val currentInstruction: Instruction
        get() = program[pc]

    var stack: Stack? = null
        private set

    // ...
}
```

## Function types and lambdas

```kotlin
// _Strings.kt
public inline fun CharSequence.all(predicate: (Char) -> Boolean): Boolean {
    //                               Function1<Char, Boolean>
    for (element in this) {
         if (!predicate(element)) return false
    // predicate.invoke(element)
    }
    return true
}


fun String.isAscii1(): Boolean {
    return this.all({ ch -> ch <= '\u007f' })
}

fun String.isAscii2(): Boolean {
    return this.all { ch -> ch <= '\u007f' }
}

fun String.isAscii3(): Boolean {
    return this.all {       it <= '\u007f' }
}
```

- If the only argument is a lambda, the parentheses can be omitted

## Custom control structures

- The last lambda argument can be moved out of the argument list

### repeat

```kotlin
// Standard.kt
public inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}


fun main() {
    repeat(10) {
        println(it)
    }
}
```

### synchronized

```kotlin
// Synchronized.kt
public inline fun <R> synchronized(lock: Any, block: () -> R): R {
    monitorEnter(lock)
    try {
        return block()
    } finally {
        monitorExit(lock)
    }
}


fun addBook(book: Book) {
    synchronized(books) {
        books.add(book)
    }
}
```

### close after use

```kotlin
val inputStream = largestDownload().inputStream().buffered()
// ... arbitrary code, might throw exception before close ...
inputStream.close()
```

```kotlin
val inputStream = largestDownload().inputStream().buffered()
inputStream.use {
    // ...
}
// ... arbitrary code, might use inputStream after close ...
```

```kotlin
largestDownload().inputStream().buffered().use { inputStream ->
    // ...
}
```

## Scope functions in vivo

### `let`

```kotlin
private var folder: String = if (Toolkit.getDefaultToolkit().screenSize.height < 1000) FOLDER_40 else FOLDER_64
```

```kotlin
private var folder: String = Toolkit.getDefaultToolkit().screenSize.height.let { screenHeight ->
    if (screenHeight < 1000) FOLDER_40 else FOLDER_64
}
```

### `apply`

```kotlin
private val randomize = JButton("🎲").apply {
    isEnabled = false
}
```

### `also`

```kotlin
val temp = result.delayed()
memory.popStackFrameUnlessEntryPoint()
return temp
```

```kotlin
return result.delayed().also { memory.popStackFrameUnlessEntryPoint() }
```

### `with`

```kotlin
var previousValue = controlPanel.slider.value

controlPanel.pause.addActionListener {
    val slider = controlPanel.slider
    if (slider.value != slider.minimum) {
        if (slider.value != slider.maximum) {
            previousValue = slider.value
        }
        slider.value = slider.minimum
    } else {
        slider.value = previousValue
    }
}
```

```kotlin
var previousValue = controlPanel.slider.value

controlPanel.pause.addActionListener {
    with(controlPanel.slider) {
        if (value != minimum) {
            if (value != maximum) {
                previousValue = value
            }
            value = minimum
        } else {
            value = previousValue
        }
    }
}
```

### `run`

```diff
-   with(controlPanel.slider)    {
+        controlPanel.slider.run {
```

### `takeIf`

```kotlin
virtualMachine = VirtualMachine(
    instructions, worldRef,
    onCall = editor::push.takeIf { compiledFromSource },
    onReturn = editor::pop.takeIf { compiledFromSource },
    onInfiniteLoop = ::onInfiniteLoop,
)
```

## Eager vs. Lazy

### Eager list

```kotlin
package people

data class Person(val name: String, val birthday: LocalDate)

val people = listOf(
    Person("Miles", LocalDate.of(1979, 3, 28)),
    Person("Toby", LocalDate.of(1986, 4, 26)),
    Person("Tina", LocalDate.of(2011, 3, 11)),
)

val fortyYearsAgo = LocalDate.now().minusYears(40)
val firstSinceThen = people
    .map { person -> println(person); person.birthday }
    .filter { day -> println(day); day.isAfter(fortyYearsAgo) }
    .first()
```

```
Person(name=Miles, birthday=1979-03-28)
Person(name=Toby, birthday=1986-04-26)
Person(name=Tina, birthday=2011-03-11)
1979-03-28
1986-04-26
2011-03-11
```

### Lazy sequence

```kotlin
val firstSinceThen = people
    .asSequence()
    .map { person -> println(person); person.birthday }
    .filter { day -> println(day); day.isAfter(fortyYearsAgo) }
    .first()
```

```
Person(name=Miles, birthday=1979-03-28)
1979-03-28
Person(name=Toby, birthday=1986-04-26)
1986-04-26
```

## Collections

### Why does this not compile?

```java
public static List<Fruit> fruitBowl() {
    if (Math.random() < 0.5) {
        return new LinkedList<Apple>();
    } else {
        return new ArrayList<Banana>();
    }
}


interface Fruit {
}

class Apple implements Fruit {
}

class Banana implements Fruit {
}
```

![](img/apples.jpg)

### For type safety!


- A subtype has all the operations of its supertype(s)
- `List<Fruit>` has an operation `void add(Fruit)`
- `List<Apple>` has no such operation
- Hence, `List<Apple>` is not a `List<Fruit>`
- Unfortunately, `Apple[]` *is* a `Fruit[]` in Java:

```java
Apple[] apples = new Apple[3];
apples[0] = new Apple();

Fruit[] fruits = apples;
fruits[1] = new Apple();

fruits[2] = new Banana(); // java.lang.ArrayStoreException: Banana
```

### Java Generics have use-site variance only

```java
public static List<? extends Fruit> fruitBowl() {
    if (Math.random() < 0.5) {
        return new LinkedList<Apple>();
    } else {
        return new ArrayList<Banana>();
    }
}
```

### Kotlin Generics also have declaration-site variance

```kotlin
fun fruitBowl(): List<Fruit> {
    if (Math.random() < 0.5) {
        return LinkedList<Apple>()
    } else {
        return ArrayList<Banana>()
    }
}
```

- Kotlin `List`s are read-only, hence this is type-safe:

```kotlin
/**
 * Methods in this interface support only read-only access to the list;
 * read/write access is supported through the [MutableList] interface.
 *
 * The list is    covariant    in its element type.
 */
public interface List<out E> : Collection<E> {
    // size
    // isEmpty
    // contains
    // get
    // indexOf
    // ...
}

/**
 * A generic ordered collection of elements
 * that supports adding and removing elements.
 *
 * The mutable list is invariant in its element type.
 */
public interface MutableList<E> : List<E>, MutableCollection<E> {
    // add
    // set
    // clear
    // remove
    // removeAt
    // ...
}
```

![](img/list.svg)

- Similar relations exist for `Set`/`MutableSet` and `Map`/`MutableMap`

### Iteration

*for each* element:

```kotlin
val fruits = listOf("apple", "banana", "cherry")

for (fruit in fruits) {
    println(fruit)
}

fruits.forEach { fruit ->
    println(fruit)
}

fruits.forEach {
    println(it)
}

fruits.forEach(::println)
```

*for each* index & element:

```kotlin
for (index in 0 until fruits.size) {
    val fruit = fruits[index]
    println("$index: $fruit")
}

for (index in 0 .. fruits.lastIndex) {
    val fruit = fruits[index]
    println("$index: $fruit")
}

for (index in fruits.indices) {
    val fruit = fruits[index]
    println("$index: $fruit")
}

for ((index, fruit) in fruits.withIndex()) {
    println("$index: $fruit")
}

fruits.forEachIndexed { index, fruit ->
    println("$index: $fruit")
}
```

*for each* key & value:

```kotlin
val fruits = mapOf("apple" to "🍎", "banana" to "🍌", "cherry" to "🍒")

for (entry in fruits) {
    println("${entry.key}: ${entry.value}")
}

fruits.forEach { entry ->
    println("${entry.key}: ${entry.value}")
}

for ((key, value) in fruits) {
    println("$key: $value")
}

fruits.forEach { (key, value) ->
    println("$key: $value")
}
```

## Katas/Dojos

- https://adventofcode.com
- https://leetcode.com/problemset/all

### Regular expressions

```kotlin
data class Address(val street: String, val streetNumber: String) {
    companion object {
        val ADDRESS: Regex = """(\D+?)\s*(\d+.*)""".toRegex()

        @JvmStatic
        fun parse(line: String): Address {
            val (street, streetNumber) = ADDRESS.matchEntire(line)?.destructured ?: error(line)
            return Address(street, streetNumber)
        }
    }
}

fun main() {
    val text = """
        Musterstr.123
        Muster-Gasse 4e
        Unter der Ulme 6g
    """.trimIndent()
    val addresses = text.lines().map(Address::parse)
    println(addresses)
}
```

- Companion objects replace `static` members
  - adopted from Scala
  - `@JvmStatic` provides `static` bridge for Java interop
- Triple quotes introduce raw strings
  - `\` instead of `\\`
  - actual line break instead of `\n`
