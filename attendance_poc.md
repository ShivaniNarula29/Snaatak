## **Attendance API Documentation for Setup & Run the attendance application**

<p align="center">
  <img src="https://5.imimg.com/data5/SELLER/Default/2022/3/DZ/AG/TW/32094369/attendance-management-software-1000x1000.png" alt="Attendance Logo" width="200"/>
</p>

## **Author Information**

| Created     | Last updated | Version | Author         | Level | Reviewer        |
|-------------|--------------|---------|----------------|-------|-----------------|
| 25-04-2025  | 25-04-2025    | V1    | Shivani Narula  | Internal Review | Siddharth Pawar |

## Table of Contents

- [Introduction](#introduction)  
- [Prerequisites](#prerequisites)  
- [System Requirements](#system-requirements)  
- [Important Ports](#important-ports)  
- [Architecture](#architecture)  
- [Flow Diagram](#flow-diagram)  
- [Step-by-Step Installation guide of Attendance API](#step-by-step-installation-guide-of-attendance-api)
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
- [Attendance API - Repo clone & setup](#attendance-api---repo-clone--setup)
- [Run the Attendance API Server](#run-the-attendance-api-server)
- [Conclusion](#conclusion)  
- [Contact Information](#contact-information)  
- [Reference Table](#reference-table)  


---

## **Introduction**
This document outlines the setup, configuration, and execution process for the Attendance REST API, a Python-based microservice designed for handling attendance data within the OT-Microservices ecosystem. This API enables efficient attendance tracking, using PostgreSQL for data storage and Redis for caching responses, ensuring quick retrieval of data for high-performance applications. Additionally, this document provides detailed steps for setting up the necessary dependencies, configuring the environment, and running the application. It covers everything from installing required packages, configuring the database, running migrations, to launching the API. Instructions for testing the application using Swagger UI are also included.

---

## **Prerequisites**
| Requirement    | Description                             |
|----------------|-----------------------------------------|
| [PostgreSQL](https://www.postgresql.org/)    | A powerful, open-source relational database system, required for managing structured data                  |
| [Redis](https://redis.io/)  | An in-memory data structure store, often used for caching and message brokering. Install the latest stable version for best performance.          |
| [Poetry](https://python-poetry.org/)    | A Python dependency management and packaging tool for managing virtual environments and dependencies.                 |
| [Liquibase](docs.liquibase.com/home.html)  | A database schema change management tool that helps track, manage, and apply database changes consistently          |

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

![image](https://github.com/user-attachments/assets/43af60ba-5103-451f-9bd9-fd96c46c75ba)

## **Flow Diagram**
![image](https://github.com/user-attachments/assets/35b40e6e-8af5-44b1-8cd5-a58480bbefa3)

## **Step-by-Step Installation guide of Attendance API**
## 1. Install PostgreSQL
➔ **Installation Steps:**

#### Step 1.1: System Update Command
> 👉 **Follow Step 3 here**: [System Update Command](https://github.com/snaatak-Downtime-Crew/Documentation/tree/main/common_stack/operating_system/ubuntu/sop/commoncommands)
>
> ---

![image](https://github.com/user-attachments/assets/4ffc2051-16a2-442b-af41-30f627ab190f)

#### Step 1.2: To install postgres directly with apt command
> 👉 **Follow Step 1 here**: [Install PostgresSql](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-1--install-from-official-website)
>
> ---

![image](https://github.com/user-attachments/assets/1e168f44-0257-46d6-818c-a4dd89651871)

#### Step 1.3: To verify the installation and check the version of postgres
> 👉 **Follow Step 2 here**: [Check PostgresSql Version](https://github.com/snaatak-Downtime-Crew/Documentation/tree/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc#step-2--to-verify-the-installation-and-check-the-version-of-postgres)
>
> ---

![image](https://github.com/user-attachments/assets/b5a16a43-65f9-48d7-af94-6ea11c68c55b)

#### Step 1.4: To start, enable, and check the status of PostgreSQL
> 👉 **Follow step 3 here**: [Software Start Enable Status etc Commands](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-3---to-start-enable-and-check-the-status-of-postgresql)
>
> ---

![image](https://github.com/user-attachments/assets/2f6808da-104c-4c1e-bcf9-c600cc168039)

#### Step 1.5: Switch user to postgres
> 👉 **Follow step 4 here**: [For switching into postgres user](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-4--for-switching-into-postgres-user)
>
> ---

![image](https://github.com/user-attachments/assets/2f6808da-104c-4c1e-bcf9-c600cc168039)


#### Step 1.6: To go into postgres shell
> 👉 **Follow step 5 here**: [Postgres Shell](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-5--to-go-into-postgres-shell)
>
> ➡️ Set a password for the default postgres user:
>```bash
> ALTER USER postgres WITH PASSWORD 'password';
>```
>
> ➡️ Create a new database named attendance_db:
>```bash
> CREATE DATABASE attendance_db;
>```
>
> ➡️ Exit from the postgres shell:
>```bash
> \q
>```
>
> ➡️ Connect to the newly created attendance_db database using psql(Test with localhost):
>```bash
> psql -h localhost -U postgres -d attendance_db
>```
>
> ---
![image](https://github.com/user-attachments/assets/3b75b97b-39c8-49a8-9b5a-5b02ed9bede2)
![image](https://github.com/user-attachments/assets/e7893b7a-b31f-4523-876b-a9e072e5e156)


#### Step 1.7: Connect PostgreSQL Database via Private IP Address
>
> ### Connect to the PostgreSQL Database Using the Server's Private IP Address
>
>➡️ Update `postgresql.conf` to listen on the private IP
>
> - Open the `postgresql.conf` configuration file:
>
>```bash
>sudo nano /etc/postgresql/14/main/postgresql.conf
>```
>
> - Find and modify the following line:
>
>```bash
>#listen_addresses = 'localhost'
>```
>
> - Change it to:
>
>```bash
>listen_addresses = '172.31.xx.xx'
>```
> **Note:** Replace `172.31.xx.xx` with your actual private IP address.
>
---

>➡️ Update `pg_hba.conf` to allow connections from the private IP
>
>- Open the `pg_hba.conf` file:
>
>```bash
>sudo nano /etc/postgresql/14/main/pg_hba.conf
>```
>
> - Add the following line at the end of the file:
>
>```bash
>host    all    all    <private_ip>/32    md5
>```
>
> **Note:** Replace `<private_ip>` with your actual private IP address.
>
---

>➡️ Restart PostgreSQL to Apply Changes
>
> - After updating the configuration files, restart the PostgreSQL service:
>
>```bash
>sudo systemctl restart postgresql
>```
>
> - Now, Check the status of PostgreSQL service:
>
>```bash
>sudo systemctl status postgresql
>```
>
> ➡️ Check the connective of prostgres using Server's private ip address
>```bash
> psql -h 172.x.x.x -U postgres -d attendance_db
>```
> **Note:** Replace `172.31.xx.xx` with your actual private IP address.
>
>---
>
![image](https://github.com/user-attachments/assets/342f024b-04e3-462d-9654-d0bf4b3770ee)

---

## 2. Install Redis
➔ **Installation Steps:**

#### Step 2.1: Install and check connectivity with localhost
> 👉 **Follow StepS 1 to 4 here**: [Install Redis Server](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-84-PRINCE/ot-ms-understanding/redis/poc/README.md#1-install-redis-server)
>
> ---

![image](https://github.com/user-attachments/assets/bb54b6d2-3991-4dbd-9cef-440d1d5e10e8)
![image](https://github.com/user-attachments/assets/02f15170-ad6b-45dd-8984-f162ae783e5c)
![image](https://github.com/user-attachments/assets/f0ce8ba7-6e74-41eb-b003-544b9a3d4907)
![image](https://github.com/user-attachments/assets/77d4beb6-912d-4604-a836-2b3b06f42f73)
![image](https://github.com/user-attachments/assets/f0536f29-c46d-4868-99d0-2e4e246da71d)

#### Step 2.2: Connect to Redis via Private IP Address  
>
>➡️ Update redis.conf to bind to the private IP address:
>
> - Open the redis.conf file:
>
>```bash
>sudo nano /etc/redis/redis.conf
>```
>
> - Find the following line:
>
>```bash
>bind 127.0.0.1
>```
>
> - Change it to:
>
>```bash
>bind '172.31.xx.xx'
>```
>
> - Also, ensure that protected mode is set to no:
>
>```bash
>protected-mode no
>```
> **Note:** Replace `172.31.xx.xx` with your actual private IP address.
>
>---
![image](https://github.com/user-attachments/assets/8074586d-2a0e-463c-b8c2-e218f3ceb1e8)

---

## 3. Liquibase (Database Change Management)
➔ **Installation Steps:**

#### Step 3.1: Install Java JDK 11 (Required for Liquibase)  
> ➡️ Install OpenJDK 11:
>
>```bash
>sudo apt install openjdk-11-jdk -y
>```
> **Note:**  Liquibase is a Java-based tool, so installing JDK 11 is mandatory.It is required to run Liquibase commands because Liquibase runs on the Java Virtual Machine (JVM).
> 
> ---
> 
![image](https://github.com/user-attachments/assets/024dc3d1-ec76-46bf-8212-fd76613ad67a)

#### Step 3.2: Install Liquibase
>
>➡️ Import the Liquibase GPG key and repository:
>
>```bash
>wget -O- https://repo.liquibase.com/liquibase.asc | gpg --dearmor > liquibase-keyring.gpg && \
>cat liquibase-keyring.gpg | sudo tee /usr/share/keyrings/liquibase-keyring.gpg > /dev/null && \
>echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/liquibase-keyring.gpg] https://repo.liquibase.com stable main' | sudo tee /etc/apt/sources.list.d/liquibase.list
>```
>
>➡️ Update the package list:
>
>```bash
> sudo apt-get update
>```

![image](https://github.com/user-attachments/assets/91a4a2ba-2813-46d0-bbe6-3c7c56a8da69)
>
>➡️ Install Liquibase:
>
>```bash
> sudo apt-get install liquibase
>```
> ---

![image](https://github.com/user-attachments/assets/ad4c4493-2efb-4b75-8d14-bb7d7d387cf9)
>
>➡️ Command to check the installed Liquibase version:
>
>```bash
> liquibase --version
>```
> **Note:**  Running this command will display the current installed version of Liquibase, helping you verify the installation.
>
> ---
>

![image](https://github.com/user-attachments/assets/6155a2af-79c7-4494-9768-c8116d2c6105)

---

## 4. Install Gunicorn, Poetry & All Other Dependencies Setup
➔ **Installation Steps:**

#### Step 4.1: Install Python & pip (Python Package Manager)
> ➡️ **Need more help installing Python?**  
> 👉 Please follow **Steps 1 to 5** here: [Install Python Guide](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/installation/guide/READEME.md#step-1-check-if-python-is-already-installed)
> 
> ```
>sudo apt update
>sudo apt install python3 -y
>sudo apt install python3-pip
>python3 --version
>pip3 --version
> ```
>---

![image](https://github.com/user-attachments/assets/208606bb-efe5-45eb-875d-42730c5476ec)
![image](https://github.com/user-attachments/assets/9d634d5f-d0f0-4202-b874-1eed615a9c57)
![image](https://github.com/user-attachments/assets/7fabf717-a976-4286-9c6d-dcfbc19b2b6a)

#### Step 4.2: Install Flask
>
>```bash
> pip3 install flask
>```
>
>----
>
#### Step 4.3: Install Poetry 
>
>```bash
> curl -sSL https://install.python-poetry.org | python3 -
>```
>
>```bash
>echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
>```
>
>```bash
>source ~/.bashrc
>```
>
>```bash
>poetry --version
>```
>
>----
>
![image](https://github.com/user-attachments/assets/060b121d-e53f-4f4d-b096-340f468ae80b)

#### Step 4.4: Install Gunicorn 
> ➡️ **Need more information about Gunicorn?**  
> 👉 Please refer to the official documentation here: [Gunicorn Installation Guide](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/python/gunicorm/sop/README.md#install-gunicorn-)
>
>```bash
>sudo pip3 install gunicorn
>gunicorn --version
>```
>
>---

![image](https://github.com/user-attachments/assets/3b754521-8984-4aa6-aa4a-060585db970c)

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
