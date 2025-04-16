
# # 🚀 React JS | SOP's for npm

![image](https://github.com/user-attachments/assets/f434b24c-9ec6-41f1-9e06-a5e7664a1183)

---

## 👤 Author Information

| Created     | Version | Author          | Comment        | Reviewer        |
|-------------|---------|------------------|----------------|------------------|
| 16-04-2025  | Version 1 | Shivani Narula  | Internal review | Siddharth Pawar  |

---

## 📚 Table of Contents

- [🧩 Introduction](#-introduction)  
- [❓ Why Use npm to Set Up React?](#-why-use-npm-to-set-up-react)  
- [📁 What is ReactJS?](#-what-is-reactjs)  
- [🔧 Prerequisites & Setup](#-prerequisites--setup)  
- [⚙️ Step 1: Install Node.js and npm](#️-step-1-install-nodejs-and-npm)  
- [📁 Step 2: Create React App](#-step-2-create-react-app)  
- [🧪 Testing Setup](#-testing-setup)  
- [🧪 Step 3: Test the Setup](#-step-3-test-the-setup)  
- [🛠️ Troubleshooting Tips](#️-troubleshooting-tips)  
- [📦 Best Practices](#-best-practices)  
- [📘 References](#-references)  
- [📇 Contacts](#-contacts)

---

## 🧩 Introduction
This document provides a detailed Standard Operating Procedure (SOP) for setting up a ReactJS application using npm (Node Package Manager). It is part of the common application stack used in modern frontend development.

ReactJS, maintained by Meta (Facebook), is a declarative, efficient, and flexible JavaScript library for building user interfaces.

---
## 📁 What is ReactJS?
ReactJS is a JavaScript library used for building interactive user interfaces. It allows developers to create large web applications that can change data, without reloading the page.

---

## ❓ Why Use npm to Set Up React?
npm is the default package manager for Node.js and is widely used in JavaScript development. Using npm to install and manage React ensures:

- Access to the latest React features
- Easy project setup using `create-react-app`
- Simplified dependency management
- Vast ecosystem of libraries and tools

---

## 🔧 Prerequisites & Setup
Before setting up React using npm, ensure the following prerequisites are met:

### ✅ System Requirements:
- Ubuntu 20.04 or higher
- Terminal/SSH access
- sudo privileges

### ✅ Tools:
- curl
- Node.js (with npm)

---

## ⚙️ Step 1: Install Node.js and npm

Run the following commands:

```bash
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs
```

📌 **Explanation:**
- First command adds NodeSource’s official repository and updates your package list.
- Second command installs Node.js and npm.

👉 Verify installation:
```bash
node -v
npm -v
```

---

## 📁 Step 2: Create React App

Use `npx` (comes with npm) to create a new React project:

```bash
npx create-react-app my-app
```

🔹 `my-app` is your project folder name. You can choose any name.

This command installs React, React-DOM, and required dependencies automatically.

---

## 🧪 Testing Setup

Go into the project directory and start the app:

```bash
cd my-app
npm start
```

Open your browser and go to:
```
http://<your-server-ip>:3000
```
You should see the default React welcome screen.

---

## 🧪 Step 3: Test the Setup

Check that the server is listening:
```bash
sudo lsof -i :3000
```

Expected output should show `node` running and listening on port `3000`.

Also verify logs/output in the terminal for build success.

---

## 🛠️ Troubleshooting Tips

| Problem | Solution |
|--------|----------|
| Port 3000 not accessible | Open the port in AWS Security Group or firewall |
| `npx` not found | Ensure Node.js and npm are correctly installed |
| App not starting | Delete `node_modules` and reinstall dependencies using `npm install` |

---

## 📦 Best Practices

- Use `.env` files for configuration
- Commit only necessary files to Git
- Use version control with `.gitignore`
- Use HTTPS in production
- Keep dependencies updated

---

## 🔗 References
- [ReactJS Official Docs](https://create-react-app.dev/docs/getting-started/)
- [Node.js Official Website](https://nodejs.org)
- [YouTube: How to Create React App using npx](https://www.youtube.com/watch?v=1HOuGMGV00g)

---

## 📇 Contact

| Name            | Email                                       |
|-----------------|---------------------------------------------|
| Shivani Narula  | shivani.narula.snaatak@mygurukulam.co       |
