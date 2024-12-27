# Better Testing with TestContainers

## Overview

This project is a companion piece for the blog post of the same name.

## Prerequisites

Before you can build or run this project, ensure that you have the following installed on your system:

- **Java Development Kit (JDK)** (version 8 or higher)
- **sbt** (version 1.5.0 or higher)
- **Scala** (optional, as sbt will manage the Scala version)

## Getting Started

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/brbrown25/blogposts.git
   cd blogposts
   ```

2. Compile the project:

   ```bash
   sbt compile
   ```

3. Run the project (if it has a main class or entry point):

   ```bash
   sbt run
   ```

4. Test the project:

   ```bash
   sbt test
   ```

## Directory Structure
```shell
├── project
├── src
   ├── main
   │   └── scala
   │       └── tutorial
   └── test
       └── scala
           └── tutorial
```