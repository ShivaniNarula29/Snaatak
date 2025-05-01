## Liquibase POC

<p align="center">
  <img src="http://miro.medium.com/v2/resize:fit:1400/1*nv2OUhfT-faVH52acTTAEQ.png" alt="Liquibase Logo" width="150"/>
</p>

---

## Author Information
| Created | Version | Last Modified | Author | Comment | Reviewer |
| --- | --- | --- | --- | --- | --- |
| 30-04-2025 | V1 | 30-04-2025 | Shivani Narula | Internal Review | Siddharth Pawar |
| 30-04-2025 | V2 | 30-04-2025 | Shivani Narula | L0 Review | Naveen Haswani |
| 30-04-2025 | V3 | 30-04-2025 | Shivani Narula | L1 Review | Deepak Nishad |
| 30-04-2025 | V4 | 30-04-2025 | Shivani Narula | L2 Review | Ashwani Singh |


## Table of Contents

- [Introduction](#introduction)  
- [Pre-requisites](#pre-requisites)  
- [Architecture Overview](#architecture-overview)  
- [Step-by-Step Setup Guide](#step-by-step-setup-guide)  
- [Best Practices](#best-practices)  
- [Backup and Rollback Strategy](#backup-and-rollback-strategy)  
- [Conclusion](#conclusion)  
- [Contact Information](#contact-information)  
- [Reference Table](#reference-table)

---

## Introduction

Liquibase is an open-source database schema change management tool. It helps developers and DevOps teams track, manage, and apply changes to relational databases in a version-controlled, automated, and repeatable manner.This POC demonstrates how Liquibase can be used for version-controlled database changes, including setup, change tracking, and rollback strategies.

---

## Pre-requisites

| Tool/Component      | Description                            |
|---------------------|----------------------------------------|
| Java (JDK 8+)        | Required to run Liquibase              |
| Liquibase CLI | Interface to apply DB changes          |
| Database             | PostgreSQL, MySQL, Oracle, etc.       |
| Git                  | For tracking changelogs               |
| JDBC Driver          | For connecting Liquibase to the DB    |
| SQL and Database Operations Knowledge | Basic understanding of SQL and database operations |

---

## Architecture Overview

![image](https://github.com/user-attachments/assets/b9e299c0-ba3e-4a3b-a788-e05296d7f258)


**Explanation**
1. Author: Developer
   Creates or updates the changelog file (changelog.xml) that defines database changes.This file is written in XML, YAML, JSON, or SQL format.

2. Version Control
   Changelog files are committed to Git or any version control system.Liquibase commands are triggered manually using the latest committed changelogs.

3. Liquibase
   Reads the changelog file and applies only unapplied changes to the database.Keeps track of applied changes using internal tables (DATABASECHANGELOG, etc.).

4. Database
   The actual target database receives schema/data updates from Liquibase.All changes are executed in order and stored for audit/history.

---

## Step-by-Step Setup Guide

### 1. Install Java Development Kit (JDK)
 Kindly use this documentation to install a specific version of Java.
 👉Reference  [Java Installation](https://github.com/snaatak-Downtime-Crew/Documentation/blob/main/common_stack/application/java/installation/script/README.md)
> ➡️ **Note:**  Liquibase is a Java-based tool, so installing JDK 8+ version.It is mandatory and required to run Liquibase commands because Liquibase runs on the Java Virtual Machine (JVM).
 
---

### 2. Install PostgresSQL 
 Kindly use this documentation to install a PostgresSQL DB.
 👉 **References here**: [Install PostgresSql](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md)

---
### 3. Install Liquibase

➡️ Import the Liquibase GPG key and repository:
>
>```bash
>wget -O- https://repo.liquibase.com/liquibase.asc | gpg --dearmor > liquibase-keyring.gpg && \
>cat liquibase-keyring.gpg | sudo tee /usr/share/keyrings/liquibase-keyring.gpg > /dev/null && \
>echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/liquibase-keyring.gpg] https://repo.liquibase.com stable main' | sudo tee /etc/apt/sources.list.d/liquibase.list
>```

---
➡️ Update the package list:
After adding the repository, update your system's package list to recognize the new repository:
>
>```bash
> sudo apt-get update
>```
---
➡️ Install Liquibase:
Install liquibase by running
>
>```bash
> sudo apt-get install liquibase
>```
---

➡️Verification
After completing the installation, ensure Liquibase is ready to use by running a simple version command:
>
>```bash
> liquibase --version
>```
---

### 4. For Connecting with DB
➡️ Find and Update Liquibase Properties file (liquibase.properties) from your project(OT-MICROSERVICES) to connect with db 
 - Replace `172.31.xx.xx` with your actual private IP address.
> 
>```bash
>sudo nano liquibase.properties
>url=jdbc:postgresql://172.31.xx.xx:5432/attendance_db
>```
---
### 5. Apply Changes

```bash
liquibase status
```
---

## Best Practices

| **Best Practice**        | **Description**                                                                 |
|--------------------------|---------------------------------------------------------------------------------|
| **Version Control**      | Store your changelog files in a version control system (e.g., Git).              |
| **Granular Changesets**  | Make changesets small and granular to facilitate easier troubleshooting and rollback. |
| **Testing**              | Always test changesets in a staging environment before applying them to production. |
| **Documentation**        | Document changesets thoroughly to ensure they are understandable by other team members. |

---

## Backup and Rollback Strategy

| Strategy Type  | Approach                                               |
|----------------|--------------------------------------------------------|
| Backup         | DB snapshot before Liquibase run (e.g., `pg_dump`)     |
| Rollback       | Use `rollback` tag or Liquibase rollback commands      |
| ChangeSet Design | Include `<rollback>` inside each `changeSet`         |

## Explanation:

➡️ **Backup**:
A backup strategy in Liquibase involves regularly saving database states to secure locations, ensuring data integrity and facilitating quick recovery in case of errors. It typically includes scheduled backups, pre-deployment backups before applying changes, and automated verification processes to ensure backup reliability.
  - Perform a database dump before applying any changes.
   
>```sh
>pg_dump -h localhost -p 5432 -U postgres -d attendance_db > backup.sql
>```

➡️ **Rollback**:
A rollback strategy in Liquibase involves reverting database changes to a previous state if deployment issues occur. It typically          includes utilizing Liquibase commands or rollback scripts to undo specific changes, ensuring data integrity by backing up before rollback, and testing rollback procedures in a staging environment for reliability.
- Define rollback procedures within your changesets.
>```xml
><changeSet id="1" author="yourname">
>   <rollback>
>        <dropTable tableName="example_table"/>
>   </rollback>
></changeSet>
>```

- Execute rollback if needed.
```sh
liquibase rollbackCount 1
```
---

## Conclusion
Liquibase is a powerful tool for managing database schema changes, providing a reliable way to track, version, and deploy changes. By following best practices and having a solid backup and rollback strategy, you can effectively manage your database schema lifecycle.


## **Contact Information**

| Name           | Email Address                             |
|----------------|-------------------------------------------|
| Shivani Narula   | shivani.narula.snaatak@mygurukulam.co       |

---

## **Reference Table**

| **Link**                                                                                                                     | **Description**                                    |
|-----------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------|
| [How to install Liquibase Database DevOps](https://chandrapurnimabhatnagar.medium.com/how-to-install-liquibase-database-devops-34ca9a6d9705) | Liquibase installation                          |
