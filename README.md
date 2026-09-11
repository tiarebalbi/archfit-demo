# archfit-demo

Companion project for the blog post **"Fitness Functions Are the Control Plane
for Agentic Coding"** (tiarebalbi.com): a small layered Kotlin service with an
executable architecture constitution enforced by [ArchUnit](https://www.archunit.org) 1.4.2.

## The constitution

Four fitness functions in
`src/test/kotlin/com/example/shop/ArchitectureConstitution.kt`:

- the domain layer depends on nothing outside itself
- web adapters never touch persistence directly
- adapters are invisible to the application core
- no dependency cycles between top-level slices

Every rule carries its rationale in `because(...)` — written for the coding
agent that will read the failure on its retry, not just for humans.

## Run it

```
./gradlew test
```

To see the gate reject an agent-style shortcut, follow `VIOLATION_DEMO.md`
(add one controller that imports a repository directly and re-run). CI runs
both paths on every push: the green pass, and the violation demo that must fail.
