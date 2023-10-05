## Enumerations

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
enum class Signal {
    GREEN, YELLOW, RED
}

enum class Phase(val active: Set<Signal>) {
    GREEN     (setOf(Signal.GREEN)),
    YELLOW    (setOf(Signal.YELLOW)),
    RED       (setOf(Signal.RED)),
    RED_YELLOW(setOf(Signal.RED, Signal.YELLOW)),










}
```

</td>
<td>

```java
enum Signal {
    GREEN, YELLOW, RED;
}

enum Phase {
    GREEN     (Set.of(Signal.GREEN)),
    YELLOW    (Set.of(Signal.YELLOW)),
    RED       (Set.of(Signal.RED)),
    RED_YELLOW(Set.of(Signal.RED, Signal.YELLOW));

    private final Set<Signal> active;

    public Phase(Set<Signal> active) {
        this.active = active;
    }

    public Set<Signal> getActive() {
        return active;
    }
}
```

</td>
</tr>
</table>

## Classes

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead(var phase: Phase) {














    fun tick() {
        phase = when (phase) {
            Phase.GREEN      -> Phase.YELLOW
            Phase.YELLOW     -> Phase.RED
            Phase.RED        -> Phase.RED_YELLOW
            Phase.RED_YELLOW -> Phase.GREEN
        }
    }
}
```

</td>
<td>

```java
class SignalHead {
    private Phase phase;

    public SignalHead(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase() {
        this.phase = phase;
    }

    public void tick() {
        phase = switch (phase) {
            case GREEN      -> Phase.YELLOW;
            case YELLOW     -> Phase.RED;
            case RED        -> Phase.RED_YELLOW;
            case RED_YELLOW -> Phase.GREEN;
        };
    }
}
```

</td>
</tr>
</table>

### Encapsulation

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead(private var phase: Phase) {




    //////// public function ////////////////

    fun getPhase() = phase



    // ...
}
```
  
</td>
<td>

```java
class SignalHead {
    private Phase phase;

    public SignalHead(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    // ...
}
```

</td>
</tr>
</table>

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead(private var _phase: Phase) {




    //////// public getter //////////////////

    val phase
        get() = _phase


    // ...
}
```

</td>
<td>

```java
class SignalHead {
    private Phase _phase;

    public SignalHead(Phase _phase) {
        this._phase = _phase;
    }

    public Phase getPhase() {
        return _phase;
    }

    // ...
}
```

</td>
</tr>
</table>

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead(phase: Phase) {
    var phase = phase
        private set

    //////// primary constructor ////////////






    // ...
}
```


</td>
<td>

```java
class SignalHead {
    private Phase phase;

    public SignalHead(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    // ...
}
```

</td>
</tr>
</table>

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead {
    var phase: Phase
        private set

    constructor(phase: Phase) {
        this.phase = phase
    }

    //////// secondary constructor //////////



    // ...
}
```

</td>
<td>

```java
class SignalHead {
    private Phase phase;


    public SignalHead(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    // ...
}
```

</td>
</tr>
</table>

<table>
<tr>
<th>Kotlin</th>
<th>Java</th>
</tr>
<tr>
<td>

```kotlin
class SignalHead() {
    var phase = Phase.RED
        private set


    //////// delegating secondary constructor

    constructor(phase: Phase) : this() {
        this.phase = phase
    }






    // ...
}
```

</td>
<td>

```java
class SignalHead {
    private Phase phase;

    public SignalHead() {
        phase = Phase.RED;
    }

    public SignalHead(Phase phase) {
        this();
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }

    // ...
}
```

</td>
</tr>
</table>

> **Exercise**
> 1. Implement an `Account` class with:
>    - `balance` (preferrably `BigInteger` or `BigDecimal`)
>    - account `number` (`String`, `Long`, some other type?)
>    - `deposit` method
>    - `withdraw` method
