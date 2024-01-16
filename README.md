# TCP Client/Server Application for Distter Protocol

## Overview

Developed as part of the IN2011 Computer Networks coursework, this Java application implements the functionality of both a TCP client and server according to the Distter RFC specifications. The program is capable of handling all five protocol requests, including the retrieval and return of posts.

---

## Project Accomplishments

- **TCP Client/Server**: Successfully created an application that can switch between client and server modes to handle TCP connections.
- **Protocol Requests**: Implemented all five types of requests outlined in the Distter RFC, ensuring full protocol support.
- **Robust Handling**: Developed the application to correctly handle incoming requests and respond appropriately, including robust error handling and input validation.

---

## Build and Run Instructions

This application is contained within a Java project that can be built and run on the course's virtual machine. A detailed `README.txt` is included within the submission, offering step-by-step instructions for compiling and executing the program.

---

## Testing

- Utilized `netcat` for peer-to-peer testing to ensure accurate communication and functionality between different instances of the program.
- Automated parts of testing by sending file contents over a TCP connection using `netcat`.
- Employed the `sha256sum` and `date` commands available on the course virtual machine to support testing related to timestamps and data integrity.

---

## Reflections

This project emphasized the practical aspects of network programming and provided a hands-on experience with TCP communication protocols. It also underscored the importance of thorough testing and collaborative development within the scope of networked applications.

---




