## **Attendance API Documentation for Setup & Run the attendance application**

<p align="center">
  <img src="https://raw.githubusercontent.com/OT-MICROSERVICES/attendance-api/5bca9615b6a5e478271687c43c0d55cf084f195b/static/attendance-api-logo.svg" alt="Attendance Logo" width="200"/>
</p>

## **Author Information**

| Created     | Last updated | Version | Author         | Level | Reviewer        |
|-------------|--------------|---------|----------------|-------|-----------------|
| 25-04-2025  | 25-04-2025    | V1    | Shivani Narula  | Internal Review | Siddharth Pawar |

## Table of Contents


- [Step-by-step installation of Attendance API](#step-by-step-installation-of-attendance-api)
  - [1. Install PostgreSQL](#1-install-postgresql)
    - [Step 1.1: System Update Command](#step-11-system-update-command)
    - [Step 1.2: To install postgres directly with apt command](#step-12-to-install-postgres-directly-with-apt-command)
    - [Step 1.3: To verify the installation and check the version of postgres](#step-13-to-verify-the-installation-and-check-the-version-of-postgres)
    - [Step 1.4: To start, enable, and check the status of PostgreSQL](#step-14-to-start-enable-and-check-the-status-of-postgresql)
    - [Step 1.5: Switch user to postgres](#step-15-switch-user-to-postgres)
    - [Step 1.6: To go into postgres shell](#step-16-to-go-into-postgres-shell)
    - [Step 1.7: Connect PostgreSQL Database via Private IP Address](#step-17-connect-postgresql-database-via-private-ip-address)




## **# Step-by-step installation of Attendance API**
## 1. Install PostgreSQL
➔ **Installation Steps:**

**Step 1.1: System Update Command**  
> 👉 **Follow Step 3 here**: [System Update Command](https://github.com/snaatak-Downtime-Crew/Documentation/tree/main/common_stack/operating_system/ubuntu/sop/commoncommands)
>
> ---

![image](https://github.com/user-attachments/assets/4ffc2051-16a2-442b-af41-30f627ab190f)

**Step 1.2: To install postgres directly with apt command**
> 👉 **Follow Step 1 here**: [Install PostgresSql](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-1--install-from-official-website)
>
> ---

![image](https://github.com/user-attachments/assets/1e168f44-0257-46d6-818c-a4dd89651871)

**Step 1.3: To verify the installation and check the version of postgres**
> 👉 **Follow Step 2 here**: [Check PostgresSql Version](https://github.com/snaatak-Downtime-Crew/Documentation/tree/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc#step-2--to-verify-the-installation-and-check-the-version-of-postgres)
>
> ---

![image](https://github.com/user-attachments/assets/b5a16a43-65f9-48d7-af94-6ea11c68c55b)

**Step 1.4: To start, enable, and check the status of PostgreSQL**
> 👉 **Follow step 3 here**: [Software Start Enable Status etc Commands](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-3---to-start-enable-and-check-the-status-of-postgresql)
>
> ---

![image](https://github.com/user-attachments/assets/2f6808da-104c-4c1e-bcf9-c600cc168039)

**Step 1.5: Switch user to postgres**
> 👉 **Follow step 4 here**: [For switching into postgres user](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-4--for-switching-into-postgres-user)
>
> ---

![image](https://github.com/user-attachments/assets/2f6808da-104c-4c1e-bcf9-c600cc168039)


**Step 1.6:  To go into postgres shell**
> 👉 **Follow step 5 here**: [Postgres Shell](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-86-Vardaan/ot-ms-understanding/postgressql/poc/README.md#step-5--to-go-into-postgres-shell)
>
> ➡️ Set a password for the default postgres user:
>```bash
> ALTER USER postgres WITH PASSWORD 'password'
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


**Step 1.7: Connect PostgreSQL Database via Private IP Address**
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
