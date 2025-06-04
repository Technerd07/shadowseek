# 🕵️‍♂️ ShadowSeek — A Private Command-Line Search Engine

> "Search the web — public or private — with no ads, no tracking, no cookies, no compromises."

<p align="center">
  <img src="https://img.shields.io/badge/Java-11%2B-blue?logo=java" alt="Java 11+">
  <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License">
  <img src="https://img.shields.io/badge/Author-Damodar%20K-orange" alt="Author">
  <img src="https://img.shields.io/badge/Privacy-First-black" alt="Privacy">
</p>

---

## 📌 Overview

**ShadowSeek** is a privacy-focused, command-line search engine built in **pure Java**. It allows you to:

- 🌐 Crawl public websites (and optionally `.onion` sites via Tor)
- 💾 Save and sanitize web content and links locally
- 🔎 Perform keyword-based searches on previously crawled content
- 📝 Log search history securely (if needed)
- 📴 Run completely offline after crawling

---

## 🗂️ Project Structure

```
shadowseek/
├── input_urls/
│   └── input_urls.txt       # List of URLs to crawl
├── lib/
│   └── jsoup-1.16.1.jar     # HTML parsing library (Jsoup)
├── logs/
│   └── search_history.txt   # Search history logs
├── pages/
│   ├── ..._content.txt      # Saved and cleaned HTML content
│   └── ..._links.txt        # Extracted links from each page
├── src/
│   ├── WebCrawler.java      # Java crawler that downloads and parses content
│   ├── PageSaver.java       # Helper class to save page content/links
│   └── CLIQuerySearch.java  # Search interface
└── README.md
```

---

## ⚙️ Features

- ✅ **Offline Keyword Search**
- 🔐 **No cookies, no tracking, no IP logging**
- 🌐 **Crawl public and deep web content** (`.onion` via Tor)
- 🧾 **Lightweight Search History Logging**
- 📄 **HTML Sanitization using Jsoup**

---

## 🚀 Setup Instructions

### 1. Requirements

- ☕ Java 11+ installed
- 📦 [Jsoup library](https://jsoup.org/download) (already included in `lib/`)

### 2. Compile the Code

```bash
javac -cp "lib/jsoup-1.16.1.jar" -d bin src/*.java
```

### 3. Crawl URLs (with or without Tor)

Make sure your URLs are in `input_urls/input_urls.txt`, then run:

```bash
# Normal crawl
java -cp "bin;lib/jsoup-1.16.1.jar" WebCrawler

# Or via Tor (if you want to crawl .onion sites)
torsocks java -cp "bin;lib/jsoup-1.16.1.jar" WebCrawler
```
*Note: On Windows, use `;` as the classpath separator.*

### 4. Run the Search Engine

```bash
java -cp "bin;lib/jsoup-1.16.1.jar" CLIQuerySearch
```

---

## 📘 Example Use Case

**1. Add URLs to Crawl**

Edit `input_urls/input_urls.txt`:

```txt
https://www.wikihow.com/Cook-Rice
https://www.thehindu.com
https://unherd.com
```

**2. Run Crawler**

This downloads HTML, extracts text/links, and stores them in `pages/`.

**3. Search Something**

```bash
Enter search query: india
```

**4. Output Example**

```
🔹 Match #1
File: https_thehindu_com_content.txt
Snippet: ... India election results updated live coverage...
```

---

## 🔒 Privacy Features

| 🛡️ Feature                | Status |
| ------------------------ | ------ |
| No cookies               | ✅      |
| No IP logging            | ✅      |
| User-Agent spoofing      | ✅      |
| Optional Tor support     | ✅      |
| No system fingerprinting | ✅      |
| Logs saved locally only  | ✅      |

---

## 🛠️ Customization

- 🔍 Modify `input_urls/input_urls.txt` to crawl new sites.
- 📝 Search logs are saved in `logs/search_history.txt`.
- 🧽 Improve sanitization in `CLIQuerySearch.java` using Jsoup's `.text()` method.
- 🌐 Enable `.onion` support by configuring Tor and using `torsocks`.

---

## 📚 Libraries Used

- [`jsoup`](https://jsoup.org) – Java HTML Parser for sanitizing content.

---

## 🧠 Future Enhancements

- [ ] 📊 Result ranking based on frequency
- [ ] 📝 Export search results as PDFs
- [ ] 🔗 Add support for Boolean operators (`AND`, `OR`, etc.)
- [ ] 🖥️ GUI wrapper for CLI

---

## 👤 Author

- **Damodar K** &nbsp; ![Author](https://img.shields.io/badge/Author-Damodar%20K-orange)

---

## 📄 License

- [MIT License](LICENSE) &nbsp; ![License](https://img.shields.io/badge/License-MIT-green.svg)

---

## 🤝 Educational Purpose Disclaimer

This project is built strictly for **educational** and **placement preparation** use. Crawling `.onion` sites or bypassing access controls should only be done in safe, ethical, and legal environments.

---
