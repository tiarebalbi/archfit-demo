package com.example.shop

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices

/**
 * The executable constitution: every rule is a fitness function an agent's
 * change must satisfy before it can merge. Failure messages are written for
 * the agent that will read them on the retry.
 *
 * Run: ./gradlew test
 */
@AnalyzeClasses(packages = ["com.example.shop"])
class ArchitectureConstitution {

    @ArchTest
    val `domain depends on nothing outside itself` = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAnyPackage("..application..", "..adapter..")
        .because(
            "the domain layer is the stable core: it must compile with zero knowledge of " +
                "services or adapters. If your change needs this dependency, move the logic " +
                "into the application layer instead of importing outward from domain."
        )

    @ArchTest
    val `web adapters never touch persistence directly` = noClasses()
        .that().resideInAPackage("..adapter.web..")
        .should().dependOnClassesThat().resideInAPackage("..adapter.persistence..")
        .because(
            "every write goes through an application service, which owns transactions and " +
                "invariants. Do not shortcut from a controller to a repository — inject the " +
                "application service and add a use-case method there if one is missing."
        )

    @ArchTest
    val `adapters are invisible to the application core` = noClasses()
        .that().resideInAnyPackage("..domain..", "..application..")
        .should().dependOnClassesThat().resideInAPackage("..adapter..")
        .because(
            "adapters are replaceable edges (web, persistence). The core defines interfaces; " +
                "adapters implement them. If the core needs a capability, declare an interface " +
                "in application and implement it in the adapter — never import the adapter."
        )

    @ArchTest
    val `no dependency cycles between top-level slices` = slices()
        .matching("com.example.shop.(*)..")
        .should().beFreeOfCycles()
        .because(
            "a cycle means two modules can only be understood together, which defeats " +
                "independent review of small changes. Break the cycle by moving the shared " +
                "type into the more stable of the two slices."
        )
}
