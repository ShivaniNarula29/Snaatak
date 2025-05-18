# Unit Testing in Python

<p align="center">
  <img src="https://keploy.io/blog/_next/image?url=https%3A%2F%2Fwp.keploy.io%2Fwp-content%2Fuploads%2F2022%2F12%2FWhat-is-unit-testing.webp&w=3840&q=75" alt="Unit test" width="300"/>
</p>

## **Author Information**

|  Version   |   Author     |  Modifed   |      Comment      |    Reviewer      |
|------------|--------------|------------|-------------------|------------------|
|  V1        | Shivani Narula |            | Internal Review   | Siddharth Pawar  |
|  V2        | Shivani Narula  |            | L0 Review         | Naveen Haswani |
|  V3        | Shivani Narula  |            | L1 Review         | Deepak Nishad |
|  V4        | Shivani Narula  |            | L2 Review         | Ashwani Singh |

## Purpose
The purpose of this document is to provide a comprehensive overview of Python Unit Testing, including its importance, tools available, best practices, and recommendations for efficient compilation processes.

## Table of Contents
- [Introduction](#-introduction)
- [What?](#-what-are-unit-testing)
- [Why?](#-why-unit-testing)
- [How](#-how)
- [Workflow Diagram](#-workflow-diagram)
- [Different Tools for Unit Testing in Python](#-different-tools-for-unit-testing-in-python)
- [Comparison of Tools](#-comparison-of-tools)
- [Advantages](#-advantages)
- [Disadvantages](#-disadvantages)
- [Best Practices](#-best-practices)
- [Recommendation](#-recommendation)
- [Conclusion](#-conclusion)
- [Contact Information](#-contact-information)
- [References](#-references)

## Introduction
Unit testing is a method of testing individual components or functions of a software application in isolation. It helps in validating that each unit of the software performs as expected.

## What ?
Unit testing refers to the process of writing and running small tests that check the correctness of individual parts (units) of a program, usually at the function level.

## Why ?
- Ensures individual code blocks work as intended.
- Makes future code changes safer.
- Helps find bugs early in development.
- Promotes modular, maintainable code.

##  How?
1. Identify functions or methods to test.
2. Use a unit testing framework (e.g., `unittest`, `pytest`).
3. Write test cases with different input scenarios.
4. Use assertions to compare actual vs expected results.
5. Run the test suite using CLI or CI tools.

## 🔁 Workflow Diagram

```mermaid
flowchart TD
    A[Write Test Cases] --> B[Run Test Suite]
    B --> C{Pass or Fail?}
    C -->|Pass| D[Continue]
    C -->|Fail| E[Debug Code]
    E --> F[Rewrite Test]
```

---

## **Different Tools for Unit Testing in Python**

| **Tool**  |  **Description**                                                  | **Why It's Used**                                                                      |  **Advantages**                                                                 |
| ------------ | ------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- |
| `unittest`   | Built-in module inspired by Java’s JUnit.                           | Used when you want a standard, built-in solution for writing and running tests.          | No external installation needed; integrates well with Python’s standard library. |
| `pytest`     | Powerful, easy-to-use framework with a rich plugin ecosystem.       | Used for writing concise tests with advanced features like fixtures and parametrization. | Cleaner syntax, better output, supports plugins and complex test scenarios.      |
| `nose2`      | Successor of `nose`, extends `unittest` functionality.              | Used when migrating from `nose` or extending `unittest`-based test suites.               | Auto test discovery, compatible with `unittest`.                                 |
| `doctest`    | Allows writing tests inside docstrings that validate documentation. | Used when you want to ensure your code examples in docs actually work.                   | Great for documentation testing, minimal setup.                                  |
| `hypothesis` | Property-based testing tool that generates test cases dynamically.  | Used to find edge cases by automatically creating a wide range of inputs.                | Detects unexpected bugs, supports generative testing.                            |

---

## Comparison of Tools

| Feature           | unittest                                                    | pytest                                                               | nose2                                              | doctest                                                       | hypothesis                                                           |
| ----------------- | ----------------------------------------------------------- | -------------------------------------------------------------------- | -------------------------------------------------- | ------------------------------------------------------------- | -------------------------------------------------------------------- |
| Built-in          | Included in Python standard library, no installation needed | Requires installation as an external package                         | Requires installation, successor of nose           | Included in Python standard library, no installation needed   | Requires installation as an external package                         |
| Plugins Support   | Very limited, no plugin ecosystem                           | Extensive plugin ecosystem with many available plugins               | Supports plugins, compatible with unittest plugins | No plugin support                                             | Supports plugins, integrates with other tools                        |
| Easy Syntax       | Moderate complexity, verbose compared to others             | Very easy and expressive syntax for writing tests                    | Easier than unittest, similar syntax               | Very simple, tests written inside docstrings                  | Easy syntax, uses decorators for test generation                     |
| Advanced Features | Basic assertions and test suites                            | Rich features like fixtures, parametrization, and detailed reporting | Basic features, extends unittest functionality     | Minimal features, mainly for documentation tests              | Property-based testing with automated input generation               |
| Auto Discovery    | Requires manual test suite definition                       | Automatically discovers tests in files and directories               | Automatically discovers tests                      | No automatic discovery, tests run only when explicitly called | No automatic discovery, depends on integration with other frameworks |

---

## Advantages of Unit Testing

| **Advantage**          | **Description**                                    |
| ---------------------- | -------------------------------------------------- |
| Early Bug Detection    | Helps catch issues early during development        |
| Code Quality           | Improves overall code structure and reusability    |
| Refactoring Safety     | Ensures changes don’t break existing functionality |
| Documentation          | Acts as live documentation for code behavior       |
| Integration with CI/CD | Seamlessly fits into automated pipelines           |

---

## Disadvantages of Unit Testing
| **Disadvantage**                       | **Description**                                                    |
| -------------------------------------- | ------------------------------------------------------------------ |
| Time-Consuming                         | Writing comprehensive tests requires significant time and planning |
| False Sense of Security                | Tests may pass even if logic is flawed (if test cases are weak)    |
| Maintenance Overhead                   | Updating tests when logic changes can be cumbersome                |
| Not a Substitute for Integration Tests | Unit tests alone can’t catch all system-level issues               |

---

## Tool-wise Advantages and Disadvantages of Unit Testing
| Tool         | Advantages                                                                                                    | Disadvantages                                                                              |
| ------------ | ------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| `unittest`   | - Built-in, no install<br>- Stable and widely known<br>- Supports test organization via classes               | - Verbose syntax<br>- Limited features<br>- Manual test discovery                          |
| `pytest`     | - Concise and readable syntax<br>- Auto test discovery<br>- Rich plugin ecosystem<br>- Detailed reporting     | - External dependency<br>- Learning curve for advanced features                            |
| `nose2`      | - Auto discovery<br>- Extends unittest features<br>- Easier transition from unittest/nose                     | - Smaller community<br>- Limited plugins<br>- Needs installation                           |
| `doctest`    | - Minimal setup<br>- Live documentation<br>- Useful for simple tests                                          | - Limited testing scope<br>- No auto discovery<br>- Not for complex tests                  |
| `hypothesis` | - Finds edge cases automatically<br>- Powerful for property-based tests<br>- Integrates with other frameworks | - Steeper learning curve<br>- External install<br>- Debugging test failures can be complex |

---

## Best Practices

| Best Practice                        | Explanation                                                                 |
|--------------------------------------|------------------------------------------------------------------------------|
| Keep Tests Independent               | Each test should run in isolation                                           |
| Use Meaningful Names                 | Test method names should reflect their purpose                              |
| Focus on One Scenario per Test       | One test should validate one behavior only                                  |
| Automate Test Execution              | Integrate tests with CI tools like Jenkins, GitHub Actions                  |
| Use Mocks and Fixtures Wisely        | Avoid external dependencies during testing                                  |
| Aim for High Coverage, but Smartly   | Coverage is useful, but focus on quality of tests over quantity             |

---

## Recommendation
We recommend using **pytest** as the primary tool for Python unit testing because it is modern, simple to use, and integrates well with CI/CD workflows.

|  **Reason**                   |  **pytest Advantage**                                       |
|--------------------------------|----------------------------------------------------------------|
| **Easy to Use**                | Simple syntax and minimal boilerplate                          |
| **Readable Output**            | Clear test failure messages and tracebacks                     |
| **Powerful Plugin System**     | Extendable via a rich ecosystem of plugins                     |
| **CI/CD Friendly**             | Seamless integration with Jenkins, GitHub Actions, etc.        |
| **Supports Fixtures and Parametrize** | Advanced features for efficient and reusable test cases      |

---

## **Unit Testing POC**

Refer to this link for [**"Step-by-Step Instructions"**]() for Unit testing in Python CI workflows.

---

## Conclusion
Unit testing is a crucial part of the software development lifecycle that helps ensure the reliability and maintainability of code. When done correctly using appropriate tools and practices, it leads to faster development, fewer bugs, and cleaner code.

---

## **Contact Information**

| Name           | Email Address                             |
|----------------|-------------------------------------------|
| Shivani Narula | shivani.narula.snaatak@mygurukulam.co    |

---

| Links                                                                                                      | Descriptions                                       |
| ---------------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| [Python unittest Documentation](https://docs.python.org/3/library/unittest.html)   |  |
| [Keploy Blog](https://keploy.io/blog)                |       |
