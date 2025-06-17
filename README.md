# SQL Assistant: Natural Language Database Querying

## 🚀 Overview

The **SQL Assistant** is an intelligent, AI-powered tool designed to bridge the gap between natural language questions and complex database queries. Say goodbye to writing SQL manually! Simply ask your data-related questions in plain English, and the assistant will effortlessly convert them into precise SQL queries, retrieve the answers, and present them back to you in an understandable, human-readable format.

## ✨ Core Capabilities

* **Natural Language to SQL Conversion:** Transform your everyday questions into executable SQL queries with ease.
* **Insightful Result Explanations:** Get clear, natural language interpretations of your query results, making data analysis accessible.
* **Continuous Improvement (Feedback System):** Help us enhance the assistant's accuracy and capabilities by providing valuable feedback on its responses.
* **Conversation History:** Maintain and review a complete history of all your past interactions and queries.
* **Support for Complex Queries:** Capable of understanding and executing intricate database inquiries involving multiple tables.

## 💡 How to Interact

The SQL Assistant provides a straightforward API interface for seamless interaction. Here’s a quick guide on how to use it:

1.  **Initiate a Query:** Send your natural language question to the assistant via the `POST` endpoint:
    `http://<<host>>/api/assistant/ask`

    Example Request Body:

        ```json
        {
            "sessionId": "your-unique-session-id",
            "question": "What are the top 5 customers by total loan amount?"
        }
        ```

2.  **Review Responses:** Upon successful query, you will receive a comprehensive response including:
    * The automatically **generated SQL query**.
    * The **raw results** fetched directly from the database.
    * A **natural language explanation** of the results.

3.  **Provide Feedback:** Your feedback is crucial for the assistant's learning and improvement. Send detailed feedback on any response to:
    `http://<<host>>/api/assistant/feedback`

    Example Request Body:

        ```json
        {
            "sessionId": "your-unique-session-id",
            "timestamp": "timestamp-of-assistant-response",
            "feedback": "The loan amount was incorrect, it should have filtered by active loans only."
        }```

        Please provide as much detail as possible to help us fine-tune its performance.

4.  **Get History:** To get the conversation history of a session, call the history endpoint:
    `http://<<host>>/api/assistant/history?sessionId=your-unique-session-id`

5.  **Clear History:** To start a new conversation or clear a session's history, call the clear endpoint:
    `http://<<host>>/api/assistant/clear?sessionId=your-unique-session-id`

## 🗄️ Database Context

The SQL Assistant is pre-configured to query a specific database schema that includes information about customers, loans, and related financial data. It leverages its understanding of these tables to generate accurate SQL.

* **Query Limits:** By default, all generated queries are automatically limited to **15 rows**, unless a different limit is explicitly specified within your natural language question (e.g., "show me all customers").

## 🎯 Tips for Effective Querying

* **Be Specific:** Formulate clear and precise questions for the most accurate and relevant results.
* **Specify Date Ranges:** Feel free to ask for data within specific date ranges (e.g., "Show me sales from January to March 2024").
* **Handle Complexity:** Don't hesitate to ask complex questions involving multiple tables or conditions.
* **Leverage Feedback:** Actively use the feedback system to guide the assistant towards better and more accurate responses.

## ⚠️ Important Considerations & Limitations

* **Under Continuous Development:** The SQL Assistant is an evolving tool and may not always provide perfect answers. We rely on your detailed feedback to continuously improve its accuracy and capabilities.
* **Evolving Database Knowledge:** While the assistant has broad knowledge of the defined schema, its understanding of specific nuances or less-frequently queried tables might still be developing. If you encounter any issues or unexpected results, please utilize the feedback system to help us enhance its database comprehension.