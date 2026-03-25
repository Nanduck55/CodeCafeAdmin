# CodeCafe Admin Dashboard

![Static Badge](https://img.shields.io/badge/Java-17-ED8B00)
![Static Badge](https://img.shields.io/badge/Javafx-19-blue)
![Static Badge](https://img.shields.io/badge/Database-MYSQL-%234479a1)
![Static Badge](https://img.shields.io/badge/Status-Active-green)
![Static Badge](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven)


## Project Description 
This is The Admin side of CodeCafe It only handles the Orders that the user will send on the Database so the Admin
can easily complete and Delete Orders. It is built using Maven and mysql database has a database helper to retrieve data.

The interface is kept simple and accessible as it is only used by the admin. This was converted using xampp oracles sql works the same so don't worry. 

## Project Team:

1. JUNIO, IAN CHRISTOPHER L.
2. SARMIENTO, BRYAN JOSEF E.
3. ORTE, RAINAN S.
4. FORONDA, MARC RENJO M.
5. BIACAN, TIFFANY QUENN D.

## Setup Instructions
Follow These Steps to ensure it runs on your device. This Covers Both Intellij and vscode.

### 1. Prerequisites
* JDK 17 or 21 above required, make sure to havr JDK installed.
* Ensure Maven is intalled (if you're using intellij its already there)
* Scene Builder (optional)

### 2. Clone The Repository
```bash
git clone [https://github.com/Nanduck55/CodeCafeAdmin.git](https://github.com/Nanduck55/CodeCafeAdmin.git)
cd CodeCafeAdmin
```

### 3. Opening the Project

#### A. Intellij IDEA
1. Open Intellij and select **file > open** (or clone to just paste the above).
2. Make sure you downloaded the entire file that means with `pom.xml`. 
   > This is a maven project it doesnt need you to set up the libraries anymore `pom.xml` got you just wait for it to download libs for you.
4. To run:
   * **DO NOT CLICK** on the green play button java will ask for libraries.
   * Instead look at the left side of Intellij click the **M**.
   * Expand CodecafeAdmin, Find `javafx` expand it, and Find `javafx:run` and youre done.
   * If an error occurs go to Execute Maven Goal (in maven next to green play) type this `mvn clean javafx:run`.

#### B. Visual Studio Code
1. Open Vscode Make sure you have Extention Pack For java and Maven for java installed if you don't then install them.
2. Go to **file > open folder** and select the project.
3. When promted click yes to import project to workspace.
4. To run:
   * **DO NOT CLICK** on the green play button java will ask for libraries.
   * Open Maven panel (find it in left sidebar).
   * Expand CodeCafeAdmin > plugins > `javafx`.
   * Click "play" next to `javafx:run`.
   * Alternatively you can just type `mvn javafx:run` on vscode terminal instead of doing allat.

### 4. Sql Setup
#### A. XAMPP
1. malek ako yapo zzz
#### B. Standard JDBC

    
    




