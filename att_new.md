## **Attendance API Documentation for Setup & Run the attendance application**

<p align="center">
  <img src="https://5.imimg.com/data5/SELLER/Default/2022/3/DZ/AG/TW/32094369/attendance-management-software-1000x1000.png" alt="Attendance Logo" width="200"/>
</p>

## **Author Information**

| Created     | Last updated | Version | Author         | Level | Reviewer        |
|-------------|--------------|---------|----------------|-------|-----------------|
| 25-04-2025  | 02-05-2025    | V1.1  | Shivani Narula  | Internal Review | Siddharth Pawar |

## Table of Contents

<details>
<summary>1. Introduction</summary>

- [Introduction](#introduction)  
- [Supported Features of the Attendance API](#supported-features-of-the-attendance-api)

</details>

<details>
<summary>2. Environment Setup</summary>

- [Pre-requisites](#pre-requisites)  
- [System Requirements](#system-requirements)  
- [Important Ports](#important-ports)

</details>

<details>
<summary>3. Installation Guide</summary>

- [Step-by-step Installation of Attendance API](#step-by-step-installation-of-attendance-api)  
  - [1. Install PostgreSQL](#1-install-postgresql)  
    - [Step 1.1: System Update Command](#step-11-system-update-command)
    - [Step 1.2: To install postgres directly with apt command](#step-12-to-install-postgres-directly-with-apt-command)
    - [Step 1.3: To verify the installation and check the version of postgres](#step-13-to-verify-the-installation-and-check-the-version-of-postgres)
    - [Step 1.4: To start, enable, and check the status of PostgreSQL](#step-14-to-start-enable-and-check-the-status-of-postgresql)
    - [Step 1.5: Switch user to postgres](#step-15-switch-user-to-postgres)
    - [Step 1.6: To go into postgres shell](#step-16-to-go-into-postgres-shell)
    - [Step 1.7: Connect PostgreSQL Database via Private IP Address](#step-17-connect-postgresql-database-via-private-ip-address)
  - [2. Install Redis](#2-install-redis)  
    - [Step 2.1: Install and check connectivity with localhost](#step-21-install-and-check-connectivity-with-localhost)
    - [Step 2.2: Connect to Redis via Private IP Address](#step-22-connect-to-redis-via-private-ip-address)
  - [3. Liquibase (Database Change Management)](#3-liquibase-database-change-management)
    - [Step 3.1: Install Java JDK 11 (Required for Liquibase)](#step-31-install-java-jdk-11-required-for-liquibase)
    - [Step 3.2: Install Liquibase](#step-32-install-liquibase)
  - [4. Install Gunicorn, Poetry & All Other Dependencies Setup](#4-install-gunicorn-poetry--all-other-dependencies-setup)
    - [Step 4.1: Install Python & pip (Python Package Manager)](#step-41-install-python--pip-python-package-manager)
    - [Step 4.2: Install Flask](#step-42-install-flask)
    - [Step 4.3: Install Poetry](#step-43-install-poetry)
    - [Step 4.4: Install Gunicorn](#step-44-install-gunicorn)

</details>

<details>
<summary>4. Configuration</summary>

- [Runtime Configuration](#runtime-configuration)  
  - [PostgreSQL Configuration](#postgresql-configuration)  
  - [Redis Configuration](#redis-configuration)

</details>

<details>
<summary>5. Code Setup</summary>

- [Attendance API - Repo Clone & Setup](#attendance-api---repo-clone--setup)  
- [Config File Settings](#config-file-settings)

</details>

<details>
<summary>6. Testing & Execution</summary>

- [Run the Attendance API Server](#run-the-attendance-api-server)  
- [Check Working](#check-working)

</details>

<details>
<summary>7. Wrap-up</summary>

- [Conclusion](#conclusion)  
- [Contact Information](#contact-information)  
- [Reference Table](#reference-table)

</details>


---

## **Introduction**
This document covers the setup, configuration, and execution of the Attendance REST API, a Python-based microservice for attendance tracking. It includes steps for installing dependencies, configuring PostgreSQL and Redis, running migrations, and testing the API with Swagger UI.

---

## **Prerequisites**
| Requirement    | Description                             |
|----------------|-----------------------------------------|
| [PostgreSQL](https://www.postgresql.org/)    | A powerful, open-source relational database system, required for managing structured data                  |
| [Redis](https://redis.io/)  | An in-memory data structure store, often used for caching and message brokering. Install the latest stable version for best performance.          |
| [Poetry](https://python-poetry.org/)    | A Python dependency management and packaging tool for managing virtual environments and dependencies.                 |
| [Liquibase](https://docs.liquibase.com/home.html)  | A database schema change management tool that helps track, manage, and apply database changes consistently          |

---

## System Requirements
| **Hardware Specifications** | **Minimum Requirement** |
|-----------------------------|-----------------------|
| **Processor**                | Dual-core 1.5 GHz     |
| **RAM**                      | 4GB                   |
| **Disk**                     | 8GB                  | 
| **Operating System (OS)**    | Ubuntu 22.04  |
| **Virtual Machine (VM)**     | t2.medium          |

---

## Dependencies
| **Name**     | **Version** | **Description**                                                   |
|--------------|-------------|-------------------------------------------------------------------|
| Poetry       | 1.8.4       | Python package manager for managing dependencies                |
| Liquibase    | 4.30.0      | For managing database migrations and updates.                   |
| PostgreSQL   | 14.13       | Relational database system to store attendance data securely.    |
| Python 3.11  | 3.10.12     | Used for running the core application and managing dependencies. |
| Redis        | 4.6.0       | In-memory key-value store for caching to improve performance.    |
| Gunicorn     | Latest      | WSGI HTTP server to host the Flask application.                 |

---

## **Important Ports**
| **Port No.** | **Service**        | **Description**                                      |
|--------------|--------------------|------------------------------------------------------|
| 22           | SSH                | Port used for secure remote login and VM access.    |
| 6379         | Redis              | Port used for Redis client connections.             |
| 8080         | Application        | Port used to run and access the application.        |
| 5432         | PostgreSQL         | Port for PostgreSQL database connections.           |
| 80/443       | HTTP/HTTPS         | Ports used for web browsing (HTTP) and secure connections (HTTPS). |

---

## **Architecture**

![Problem Statement-Page-21 (2)](https://github.com/user-attachments/assets/bc732677-7903-4be6-9226-3ad9c2b198e3)

## **Flow Diagram**

![image](https://github.com/user-attachments/assets/4d337acd-39a6-4cf8-90db-3440b7fc2d70)

## **Step-by-Step Installation Guide**

➡️ *Update your server* [Go to this link for ubuntu basic commands.](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/operating_system/ubuntu/sop/commoncommands/README.md#1-basic-system-commands)

---

## 1. Install PostgreSQL

➡️ *Install the postgreSQL*: [Go to this link for postgresSQL installation guide](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/postgresql/installation/guide/README.md)

---

## 2. Redis

➡️ *Install redis-server*: [Go to this link for Redis-server installation guide](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-84-PRINCE/common_stack/software/redis/installation/README.md#1-install-redis-server)

---

## 3. Liquibase (Database Change Management)

➡️ *Install Liquibase*: [Go to this link for Redis-server installation guide](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-84-PRINCE/common_stack/software/redis/installation/README.md#1-install-redis-server)

---

## 4. Gunicorn

➡️ *Install Python*: [Install Gunicorn Guide]([https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/installation/guide/READEME.md#step-1-check-if-python-is-already-installed](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/installation/guide/READEME.md#step-1-check-if-python-is-already-installed))

---
## 5. Poetry 

➡️ *Install poetry*: [Install Gunicorn Guide]([https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/installation/guide/READEME.md#step-1-check-if-python-is-already-installed](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/installation/guide/READEME.md#step-1-check-if-python-is-already-installed))

---

## **Attendance API - Repo Clone & Setup**
**Step 1: Git Clone** 
> ➡️ **Attendance api repository**
> 
>```bash
>git clone https://github.com/OT-MICROSERVICES/attendance-api.git
>ls
>```
> ➡️ **After clone go to the repo clone folder**
> 
>```bash
> cd attendance-api/
>ls
>```
>---

![image](https://github.com/user-attachments/assets/38ff0ca0-89cd-428e-bcaa-1c9f9a0a9e91)

> ➡️ **Now, Update configuration files to listen on the private IP**
> 
> - Open the `liquibase.properties` configuration file:
>
>```bash
> sudo nano liquibase.properties
>```
>
> - Find and modify the following line:
>
>```bash
>url=jdbc:postgresql://127.0.0.1:5432/attendance_db
>```
>
> - Change it to:
>
>```bash
>url=jdbc:postgresql://172.31.xx.xx:5432/attendance_db
>```
> **Note:** Replace `172.31.xx.xx` with your actual private IP address.
>
> - Save it:
>
>```bash
>ctrl+x
>```
>
---

> ➡️ **Update config.yaml to listen on the private IP**
> 
> - Open the `config.yaml` configuration file:
>
>```bash
>  sudo nano config.yaml
>```
>
> - Find and modify the following line:
>
>```bash
>host: 127.0.0.1
>```
>
> - Change it to:
>
>```bash
>host: 172.31.xx.xx
>```
> **Note:** Replace `172.31.xx.xx` with your actual private IP address.
>

---
## **Run the Attendance API Server**

**Step 1:Install pip dependencies** 
>```bash
>cd attendance-api
>```
>
> ```bash
>pip install Flask gunicorn flasgger prometheus-flask-exporter redis dataclasses-json psycopg2-binary
>```
>
> ```bash
>pip install Flask-Caching
>```
>
> ```bash 
>pip install python-json-logger
>```
>
> ```bash
>pip install voluptuous
>```
>
> ```bash
>pip install peewee
>```
> 
>---

![image](https://github.com/user-attachments/assets/a125c188-bd9f-408e-af21-be3504897037)
![image](https://github.com/user-attachments/assets/957f0d66-1e97-4302-8408-eb668b01ea1e)
![image](https://github.com/user-attachments/assets/f5d71639-8697-4b9c-a231-fbae4376af5e)

**Step 2:Run Migration**
> ➡️ **Install make**
> 
>```bash
>sudo apt install make -y
>make --version
>make run-migrations
>```
>
>---
![image](https://github.com/user-attachments/assets/b8de4582-5805-4480-a437-7788c458c44f)
![image](https://github.com/user-attachments/assets/6cc881e9-8ccc-4c22-aea8-fb5bf4ffa7df)

**Step 3:Run the application** 
> ➡️ **Create Service File:**
> 
>```bash
>sudo nano /etc/systemd/system/gunicorn.service
>```
>
> ➡️ **Paste below content in our service file `gunicorn.service`**
>
> ```bash
>[Unit]
>Description=gunicorn daemon for the Attendance API
>After=network.target
>
>[Service]
>User=ubuntu
>Group=ubuntu
>WorkingDirectory=/home/ubuntu/attendance-api
>ExecStart=/usr/local/bin/gunicorn --workers 3 --bind 0.0.0.0:8080 app:app
>
>[Install]
>WantedBy=multi-user.target
>```
>
> ➡️ **Now,Start the 'gunicorn.service'**
> 
>```bash
> sudo systemctl daemon-reload
> sudo systemctl enable gunicorn
> sudo systemctl start gunicorn 
> sudo systemctl status gunicorn 
>
>```
>---
> 
![image](https://github.com/user-attachments/assets/c01977e6-f330-43f4-bf9d-595d7b40a9ff)

**Step 4 :Open any browser and check the swagger page will be accessible on http://public-ip:8080/apidocs** 
>---
![image](https://github.com/user-attachments/assets/6d496183-27ef-473a-969a-e0152a4571ff)
![image](https://github.com/user-attachments/assets/cca22fcb-c2ad-4b9e-835e-152319d4e023)
![image](https://github.com/user-attachments/assets/cc846eca-b24c-4633-a429-982b94666e19)

---
## **Conclusion**

This API is a tool that helps businesses keep track of their employees' attendance. It's designed to be easy to use, reliable, and secure. By using this API, we can automate the process of recording attendance, generating reports, and sending reminders.

---

## **Contact Information**

| Name           | Email Address                             |
|----------------|-------------------------------------------|
| Shivani Narula   | shivani.narula.snaatak@mygurukulam.co       |

---

## **Reference Table**

| **Link**                                                                                                                     | **Description**                                    |
|-----------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------|
| [How to install and configure PostgreSQL on Ubuntu 20.04](https://medium.com/devops-technical-notes-and-manuals/how-to-install-and-configure-postgresql-on-ubuntu-20-04-4fd3cf072d6f) | PostgreSQL installation and configuration       |
| [How to install Liquibase Database DevOps](https://chandrapurnimabhatnagar.medium.com/how-to-install-liquibase-database-devops-34ca9a6d9705) | Liquibase installation                          |
| [Redis POC Documentation](https://github.com/avengers-p11/Documentation/blob/main/OT%20MS%20Understanding/Redis/Redis%20POC/README.md) | Redis                        

