# 🧠 SQL Assistant – AI Powered Natural Language Querying for Databases

## 🚀 Overview
SQL Assistant is a Proof of Concept (POC) developed using Spring Boot to enable natural language interaction with relational databases.

Instead of writing SQL manually, users can ask questions in plain English. The system:
1. Translates natural language into accurate SQL queries.
2. Executes the queries against the target database.
3. Returns both raw query results and a human-readable explanation.

This POC demonstrates the power of combining AI + databases, making data access easier for both technical and non-technical users.

📌 GitHub Repository: [SQL Assistant GitHub Link](<YOUR_GITHUB_URL>)

---

## 🌟 Key Features
- Natural Language → SQL Conversion  
- Human-Friendly Explanations  
- Supports Complex Queries (joins, aggregations, conditions)  
- Feedback Mechanism for improvements  
- Session History Management  
- Clear Session option for fresh usage  

---

## 🏗️ Architecture
**Workflow:**
1. User sends a natural language query to REST API.  
2. AI engine converts query to SQL.  
3. SQL executes against the relational database.  
4. Results + explanation returned.  
5. Feedback collected to refine accuracy.  

**Components:**
- Spring Boot REST API  
- AI Layer (NL-to-SQL)  
- Database integration (Customers, Loans, EMI Payments, Financial Data)  
- Session & Feedback Management  

---

## 📡 API Reference

### 1. Ask a Question  
Endpoint: POST /api/assistant/ask  
Request Body:  
{
  "sessionId": "unique-session-id",
  "question": "What are the top 5 customers by total loan amount?"
}

---

### 2. Submit Feedback  
Endpoint: POST /api/assistant/feedback  
Request Body:  
{
  "sessionId": "unique-session-id",
  "timestamp": "timestamp-of-response",
  "feedback": "The loan amount should include only active loans."
}

---

### 3. Retrieve Session History  
Endpoint: GET /api/assistant/history?sessionId=unique-session-id

---

### 4. Clear Session History  
Endpoint: POST /api/assistant/clear?sessionId=unique-session-id

---

## 🗃️ Database Schema Context
Schema designed for financial services:  
- Customers  
- Loans  
- EMI Payments  
- Related Financial Data  

⚠️ By default, results are limited to 15 rows, unless otherwise requested.  

---

## 💡 Best Practices
- Be specific with queries (filters, date ranges, conditions).  
- Provide feedback to refine accuracy.  
- Try multi-entity queries for deeper insights.  

---

## ⚠️ Limitations
- 🚧 POC Stage – Accuracy may vary.  
- 📌 Schema Bound – Queries limited to known schema.  
- 📊 Row Limit – Restricted to 15 rows by default.  

---

## 📌 Next Steps & Extensions
- Multi-schema / cross-database support  
- User-specific query optimization  
- Visual dashboards for results  
- BI tool integration  
