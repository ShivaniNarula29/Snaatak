# **POC of Unit Testing in Python Using pytest**


<p align="center">
  <img src="https://i.ytimg.com/vi/YbpKMIUjvK8/maxresdefault.jpg" alt="Unit test" width="300"/>
</p>


## **Author Information**

|  Version   |   Author     |  Modified   |      Comment      |    Reviewer      |
|------------|--------------|------------|-------------------|------------------|
|  V1        | Shivani Narula |   18-05-2025         | Internal Review   | Siddharth Pawar  |
|  V2        | Shivani Narula  |            | L0 Review         | Naveen Haswani |
|  V3        | Shivani Narula  |            | L1 Review         | Deepak Nishad |
|  V4        | Shivani Narula  |            | L2 Review         | Ashwani Singh |

---

## Table of Contents

- [Introduction](#introduction)

- [Contact Information](#contact-information)
- [References](#references)



## Introduction
 In this Document we are creating the POC of unit testing for the Pyhton based API, to get the errors by performing the unit testing.


## Pre-requisite


## Run Time Dependency

| **Name** | **Version** | **Description** |
|------|---------|-------------|
| **python** | 3.11 | Application is built on this version only |


##  Step-by-step Installation

# Clone the repo
```
sudo apt insatll git
git clone https://github.com/OT-MICROSERVICES/attendance-api.git
cd attendance-api
sudo apt install python3-pip
pip install pytest
pytest
pip install pytest-mock
PYTHONPATH=. pytest
pip install pytest-html
PYTHONPATH=. pytest --html=report.html --self-contained-html
 ```
This command will start executing unit tests.

![image](https://github.com/user-attachments/assets/567d3a51-2c27-45c8-b216-e9c9945957d4)
![Screenshot 2025-05-18 162804](https://github.com/user-attachments/assets/aad1f235-47e0-4ea4-a32c-e9276d3e0bdb)
![image](https://github.com/user-attachments/assets/6d4538e4-6685-48b2-9851-dc5830afd778)
![Screenshot 2025-05-18 203619](https://github.com/user-attachments/assets/daf31114-44d4-4241-a67b-51133fabe252)
![image](https://github.com/user-attachments/assets/acb6f2de-9fce-4fb2-8f45-d8ac66ce9c06)
![image](https://github.com/user-attachments/assets/f54f2aa0-bb49-4012-9e9f-1f2857b8e632)
![image](https://github.com/user-attachments/assets/d4b92329-bca9-46d4-b1e0-218ff70b7c72)



















# Attendance API - Unit Testing Guide

This document provides a step-by-step guide to install dependencies and run unit tests for the [Attendance API](https://github.com/OT-MICROSERVICES/attendance-api).

---

## ✅ Step-by-step Installation

### 🔹 1. Clone the Repository

Install Git (if not already installed):

```bash
sudo apt install git
```

Clone the Attendance API repository:

```bash
git clone https://github.com/OT-MICROSERVICES/attendance-api.git
cd attendance-api
```

![image](https://github.com/user-attachments/assets/567d3a51-2c27-45c8-b216-e9c9945957d4)

---

### 🔹 2. Install Python and Pytest

Install Python pip and run initial tests:

```bash
sudo apt install python3-pip
pip install pytest
pytest
```

![Screenshot 2025-05-18 162804](https://github.com/user-attachments/assets/aad1f235-47e0-4ea4-a32c-e9276d3e0bdb)

---

### 🔹 3. Add Mock Testing

Install `pytest-mock` and re-run tests with Python path:

```bash
pip install pytest-mock
PYTHONPATH=. pytest
```

![image](https://github.com/user-attachments/assets/6d4538e4-6685-48b2-9851-dc5830afd778)

---

### 🔹 4. Generate HTML Test Report

Install `pytest-html` and generate the test report:

```bash
pip install pytest-html
PYTHONPATH=. pytest --html=report.html --self-contained-html
```

![Screenshot 2025-05-18 203619](https://github.com/user-attachments/assets/daf31114-44d4-4241-a67b-51133fabe252)

---

### ✅ Output Screenshots

#### ✔️ Sample Terminal Outputs

- Pytest Run:

  ![image](https://github.com/user-attachments/assets/acb6f2de-9fce-4fb2-8f45-d8ac66ce9c06)

- Report Confirmation:

  ![image](https://github.com/user-attachments/assets/f54f2aa0-bb49-4012-9e9f-1f2857b8e632)

#### ✔️ HTML Report Preview

![image](https://github.com/user-attachments/assets/d4b92329-bca9-46d4-b1e0-218ff70b7c72)

---

In the above images we can see that this unit test give us the modules error , same as we are facing when we are building our api


 #### Errors we are Facing
| **Error Type**           | Description | **Possible Solutions**  |
|--------------------------|---------------------------|----------------------|
|Modules| Raised when the Python interpreter can't find the module to import|Ensure the module is installed,Ensure the module is in the correct path or location|
|Flask|Raised when Python can't find the flask|Please make sure that all libraries of python is installed properly|
|psycopg2|error occurs when the PostgreSQL server is unreachable, either due to connection issues or incorrect connection parameters.|Ensure that the PostgreSQL server is running and accessible.|


## Report Link
Refers to the link [Unit Testing](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-165-SHIVANI/application-ci/checks/python/unit-testing/poc/output/report.html)

## Conclusion
 we can say that Unit testing in Python ensures code correctness, identifies bugs early, improves code reliability, and promotes efficient debugging and maintenance.

