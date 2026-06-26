# POS Terminal Agent

Java 17 application for Linux POS terminals.

## Components

### Agent
Runs on each POS terminal and:
- Collects hardware and system information
- Checks into RSAM every 5 minutes
- Listens for remote commands

### Server
Simple HTTP server used during development to receive check-ins from the agent.

## Requirements

- Java 17
- Maven