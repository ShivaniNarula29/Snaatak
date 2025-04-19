# SOP for **npm (Node Package Manager)**

<p align="center">
  <img src="https://raw.githubusercontent.com/npm/logos/master/npm%20logo/npm-logo-red.png" alt="NPM Logo" width="200"/>
</p>

---

## Author Information

| Created | Version | Last Modified | Author | Comment | Reviewer |
| --- | --- | --- | --- | --- | --- |
| 16-04-2025 | V1.0 | 17-04-2025 | Shivani Narula | npm-specific SOP | Siddharth Pawar |

---

## Table of Contents

- [Introduction](#-introduction)
- [Prerequisites & Setup](#-prerequisites--setup)
- [Installation Steps](#-installation-steps)
- [Verifying npm Installation](#-verifying-npm-installation)
- [Common npm Commands](#-common-npm-commands)
- [Install & Usage Examples](#install--usage-examples)
- [Troubleshooting](#-troubleshooting)
- [Best Practices](#-best-practices)
- [References](#-references)
- [Contacts](#-contacts)

---

## Introduction

This SOP provides step-by-step instructions to **install and configure npm (Node Package Manager)**. npm is the world’s largest software registry and the default package manager for Node.js, enabling developers to install and manage JavaScript packages and libraries efficiently.

---

## 🔧 Prerequisites & Setup

### ✅ System Requirements (Ubuntu Linux)

| Component   | Minimum Requirement             |
|------------|----------------------------------|
| OS         | Ubuntu 18.04 or later            |
| Privileges | sudo/root access required        |
| Tools      | curl (for installation script)   |
| Internet   | Required                         |

---

## 🛠 Installation Steps

### 1. **Install `curl` (if not installed)**
```bash
sudo apt update
sudo apt install curl -y
```

### 2. **Install Node.js (npm comes bundled)**
```bash
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt install -y nodejs
```
> 📌 This installs both Node.js and npm in one step using the NodeSource repository.

---

## ✅ Verifying npm Installation

Run the following commands to check installation:
```bash
node -v   # Shows Node.js version (e.g., v18.19.0)
npm -v    # Shows npm version (e.g., 9.6.7)
```

---

## ↻ Common npm Commands

| Command | Purpose |
|--------|---------|
| `npm install <package>` | Install a package locally |
| `npm install -g <package>` | Install a package globally |
| `npm uninstall <package>` | Uninstall a local package |
| `npm update` | Update all local packages |
| `npm list` | View installed packages |
| `npm list -g --depth=0` | View globally installed packages |
| `npm audit` | Run security check on installed packages |
| `npm cache clean --force` | Clear the npm cache |

---

##  Install & Usage Exampls 

Run these 4 commands to demonstrate npm usage:
```bash
npm install cowsay          # Install locally
npx cowsay "Hello Reviewer" # Run installed package

npm list                    # View installed packages
npm uninstall cowsay        # Clean up
```
> Perfect for live demo to reviewers – shows install, run, list, and uninstall cycle clearly.

---

## 🧪 Troubleshooting

| Issue | Solution |
|-------|----------|
| `npx` or `npm` not found | Reinstall Node.js and check PATH |
| Permission errors | Use sudo or fix with nvm (Node Version Manager) |
| Version mismatch | Run `npm install -g npm@latest` |

---

## ✅ Best Practices

- 🔒 Never commit `node_modules` to your repository.
- 📄 Use a `package-lock.json` to ensure consistent dependency versions.
- 💡 Prefer local installs unless truly needed globally.
- ↻ Regularly run `npm audit` for security checks.
- 🧹 Run `npm cache clean --force` occasionally to keep things tidy.

---

## 📜 References

| Link | Description |
|------|-------------|
| https://www.npmjs.com/ | Official npm site |
| https://nodejs.org | Node.js official website |
| https://docs.npmjs.com/ | npm Documentation |

---

