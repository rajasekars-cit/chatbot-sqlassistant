# 🧠 SQL Assistant – AI Powered Natural Language Querying for Databases

## 🚀 Overview

**SQL Assistant** is an AI powered Spring Boot application that enables users to interact with relational databases using natural language. Instead of writing complex SQL queries manually, users can simply ask questions in plain English. The assistant automatically generates accurate SQL, executes it, and presents both the raw results and a human-readable explanation.

## 🌟 Features

- **Natural Language to SQL Conversion**  
  Seamlessly convert user queries into syntactically correct SQL statements.

- **Human-Friendly Result Interpretation**  
  Understand your data through clear, natural language explanations of SQL output.

- **Feedback-Driven Learning**  
  Submit feedback to improve the assistant’s accuracy over time.

- **Conversation History Management**  
  Track and revisit previous sessions and queries with ease.

- **Support for Complex Queries**  
  Handle advanced data inquiries across multiple related tables and conditions.

## 📡 API Endpoints

Interact with the SQL Assistant using the following RESTful endpoints:

### 1. Ask a Question

Send a question in natural language and get the generated SQL, results, and explanation.

**Endpoint:**
```
POST http://<host>/api/assistant/ask
```

**Request Body:**
```json
{
  "sessionId": "your-unique-session-id",
  "question": "What are the top 5 customers by total loan amount?"
}
```

---

### 2. Submit Feedback

Help improve the assistant by submitting corrections or suggestions.

**Endpoint:**
```
POST http://<host>/api/assistant/feedback
```

**Request Body:**
```json
{
  "sessionId": "your-unique-session-id",
  "timestamp": "timestamp-of-assistant-response",
  "feedback": "The loan amount should include only active loans."
}
```

---

### 3. Retrieve Session History

Get the complete conversation history for a given session.

**Endpoint:**
```
GET http://<host>/api/assistant/history?sessionId=your-unique-session-id
```

---

### 4. Clear Session History

Start fresh by clearing the history of a session.

**Endpoint:**
```
POST http://<host>/api/assistant/clear?sessionId=your-unique-session-id
```

## 🗃️ Database Schema Context

The assistant is designed to work with a predefined schema involving:

- Customers  
- Loans  
- EMI payments  
- Related financial data

It uses this schema knowledge to generate relevant and correct queries.

> **Note:** Queries are limited to **15 rows** by default unless otherwise specified in the user input (e.g., “show all customers”).

## 💡 Best Practices for Effective Use

- **Be Specific**  
  Ask clear, focused questions for better precision.

- **Include Filters**  
  Specify date ranges, conditions, or attributes to narrow results.

- **Ask Complex Questions**  
  Don’t hesitate to query across multiple entities or include joins and filters.

- **Give Feedback**  
  Use the feedback endpoint regularly to improve future results.

## ⚠️ Limitations

- **Work in Progress**  
  The assistant is continuously evolving. Some responses may not be accurate initially.

- **Schema-Dependent**  
  The assistant’s capabilities are bound to the database schema it understands. For best results, ensure queries align with the available structure.
