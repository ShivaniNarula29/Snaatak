# Redis Documentation

<p align="center">
  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJrUIPz92r6o3cC76UZAYuU1vb7tmpCPmZAw&s" alt="Attendance Logo" height="100"width="200"/>
</p>

## Author Information

| **Created**       | **Version** | **Last Modified** | **Author**        | **Level**            | **Reviewer**  |
|--------------------|-------------|-------------------|-------------------|----------------------|---------------|
|   27-04-2025       |     V1      |                   |  Shivani Narula   | Internal review      | Siddharth Pawar |
|   27-04-2025       |             |                   |  Shivani Narula   | L0 Review            | Naveen Haswani |
|   27-04-2025       |             |                   |  Shivani Narula   | L1 Review            | Deepak Nishad |
|   27-04-2025       |             |                   |  Shivani Narula   | L2 Review            | Ashwani |

---

## Table of Contents

- [Introduction](#introduction)
- [Purpose](#purpose)
- [Why We Choose Redis](#why-we-choose-redis)
- [Advantages](#advantages)
- [Disadvantages](#disadvantages)
- [Use Cases](#use-cases)
- [Installation and Configuration](#installation-and-configuration)
- [Important Ports](#important-ports)
- [Conclusion](#conclusion)
- [Contact](#contact)
- [References](#references)

---

## Introduction

This document explains Redis — an open-source, very fast, in-memory database. Redis stores data in the system’s memory (RAM), which makes reading and writing extremely quick.It is mainly used for caching, real-time data processing, messaging, and session storage.

---

## Purpose

Redis helps applications run faster by storing frequently used data in memory instead of hard disks.It is designed for scenarios where speed is critical, like caching user data, real-time leaderboards, and messaging systems.

---

## Why We Choose Redis

Redis was chosen because it offers a very high speed for data access and supports multiple advanced data structures like strings, lists, sets, and hashes. It also provides persistence options, clustering, and replication, which makes it reliable for both temporary and durable storage needs without complex setups.

---

## Advantages

| **Advantage**                    | **Description**                                                                 |
|-----------------------------------|---------------------------------------------------------------------------------|
| Very High Speed                  | Operations take less than a millisecond.                                        |
| Easy to Use                      | Simple commands and easy setup.                                                 |
| Supports Multiple Data Types     | Strings, lists, sets, sorted sets, hashes, bitmaps, etc.                        |
| Data Durability                  | Options to save data on disk periodically.                                      |
| High Availability and Scaling    | Master-slave replication and cluster support.                                  |
| Pub/Sub Messaging System         | Supports publish/subscribe messaging out of the box.                           |

---

## Disadvantages

| **Disadvantage**                  | **Description**                                                                 |
|-----------------------------------|---------------------------------------------------------------------------------|
| Memory Dependent                  | Everything is stored in RAM, so large datasets need large memory.               |
| Persistence Can Slow Down         | Saving data to disk can slightly affect speed.                                  |
| Single-threaded                   | By default, Redis processes commands one at a time (though it is very fast).    |
| Risk of Data Loss                 | If not configured properly for persistence, data may be lost after a crash.     |

---

## Use Cases

| **Use Case**                      | **Description**                                                                 |
|-----------------------------------|---------------------------------------------------------------------------------|
| Caching Frequently Used Data      | Reduces the load on databases and speeds up applications.                      |
| Session Storage                  | Stores user sessions in web applications (like login info).                    |
| Real-time Analytics               | Tracks user activity, page views, etc., instantly.                             |
| Message Queues                    | Acts as a simple queue for background jobs.                                     |
| Gaming Leaderboards               | Stores scores and rankings using sorted sets.                                  |
| Distributed Locking               | Helps manage access control to resources across distributed systems.            |

---

## Installation and Configuration

- Follow the [link](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-84-PRINCE/ot-ms-understanding/redis/poc/README.md) for detailed installation and setup instructions.

---

## Important Ports

- Redis by default runs on **Port 6379**.
- More port details are available [here](https://github.com/snaatak-Downtime-Crew/Documentation/blob/SCRUMS-84-PRINCE/ot-ms-understanding/redis/poc/README.md#important-ports).

---

## Conclusion

Redis is a perfect choice when you need fast, reliable, and simple-to-use storage. It is trusted worldwide for caching, real-time messaging, and high-speed data access.Its lightweight design and great features make it very useful in modern applications.

---

## Contact

| **Name**           | **Email Address**                                 |
|---------------------|--------------------------------------------------|
| Shivani Narula     | shivani.narula.snaatak@mygurukulam.co             |

---

## References

| **Link** | **Description** |
|:---------|:----------------|
| [Guide to Fully Understanding Redis](https://blog.logrocket.com/guide-to-fully-understanding-redis/) | A detailed guide for fully understanding Redis concepts. |
| [Official Redis Documentation](https://redis.io/docs/) | Official documentation covering Redis features, commands, and best practices. |
| [Redis GitHub Repository](https://github.com/redis/redis) | Redis source code, updates, and community contributions. |
