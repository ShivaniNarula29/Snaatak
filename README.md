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
- [References](#references)

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


### 2. For Connecting with DB

```bash
mkdir liquibase-poc && cd liquibase-poc
```

Example `liquibase.properties`:
```properties
changeLogFile: changelog.xml
url: jdbc:postgresql://localhost:5432/mydb
username: dbuser
password: dbpass
driver: org.postgresql.Driver
```

---

### 3. Define a ChangeSet (changelog.xml)

```xml
<databaseChangeLog
  xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
  http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd">

  <changeSet id="1" author="dev">
    <createTable tableName="employee">
      <column name="id" type="int">
        <constraints primaryKey="true"/>
      </column>
      <column name="name" type="varchar(255)"/>
    </createTable>
  </changeSet>
</databaseChangeLog>
```

---

### 4. Apply Changes

```bash
liquibase update
```

---

### 5. Rollback Example

```xml
<changeSet id="2" author="dev">
  <dropTable tableName="employee"/>
</changeSet>
```

```bash
liquibase update
liquibase rollbackCount 1
```

---

## 🧠 Best Practices

- Use semantic versioning in `changeSet` IDs.
- Modularize changes across multiple changelog files.
- Use rollback tags inside `changeSet`.
- Store changelogs in version control.
- Avoid hard-coded DB credentials—use environment variables or secret managers.

---

## 🔄 Backup and Rollback Strategy

| Strategy Type  | Approach                                               |
|----------------|--------------------------------------------------------|
| Backup         | DB snapshot before Liquibase run (e.g., `pg_dump`)     |
| Rollback       | Use `rollback` tag or Liquibase rollback commands      |
| ChangeSet Design | Include `<rollback>` inside each `changeSet`         |

Example:
```xml
<rollback>
  <dropColumn tableName="employee" columnName="email"/>
</rollback>
```

---

## 🏁 Conclusion

Liquibase helps automate and manage database schema changes in a controlled, trackable, and repeatable way. This POC provides a solid foundation for integrating Liquibase into your DevOps pipeline.

---

## 📞 Contact Information

| Role         | Name         | Email                     |
|--------------|--------------|---------------------------|
| POC Owner    | Your Name    | your.email@example.com    |
| DevOps Lead  | DevOps Lead  | devops@example.com        |

---

## 📚 References

- [Liquibase Official Docs](https://www.liquibase.org/documentation/index.html)
- [Liquibase GitHub](https://github.com/liquibase/liquibase)
- [Liquibase Docker Hub](https://hub.docker.com/r/liquibase/liquibase)
- [CI/CD Integration Guide](https://docs.liquibase.com/workflows/liquibase-community/automate-with-ci-cd-tools.html)
