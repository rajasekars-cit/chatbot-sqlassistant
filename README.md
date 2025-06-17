## Overview
The SQL Assistant is an AI-powered tool that helps you query and analyze database information using natural language. Simply ask questions in plain English, and the assistant will convert them into SQL queries and provide you with answers.

## Features
- Natural language to SQL conversion
- Clear explanations of query results
- Feedback system for continuous improvement
- History tracking of all conversations
- Support for complex database queries

## How to Use
1. **Ask Questions**: Pass your question to api http://<<host>>/api/assistant/ask
2. **View Results**: See the generated SQL query, raw results, and a natural language explanation
3. **Provide Feedback**: Pass feedback to api http://<<host>>/api/assistant/feedback on any response so that we can improve the assistant to answer your questions better, so please provide as much detail as possible.
4. **Clear History**: Call clear api to clear the chat history and start new conversation

## Database Schema
The assistant has access to various tables containing information about customer, loans, and related data. All queries are automatically limited to 15 rows unless specified otherwise.

## Tips
- Be specific in your questions for better results
- You can ask for data within specific date ranges
- The assistant can handle complex queries involving multiple tables
- Use the feedback system to help improve responses 

## Note!!
- The assistant is not perfect and may make mistakes. Please use the feedback system to improve your responses.
- The db knowledge is still incomplete for some tables, so if encountered any issues, please use the feedback system to improve the assistant.