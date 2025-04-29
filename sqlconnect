#include <iostream>
#include <sqlite3.h>

using namespace std;

static int callback(void *NotUsed, int argc, char **argv, char **azColName) {
    for (int i = 0; i < argc; i++) {
        cout << azColName[i] << ": " << (argv[i] ? argv[i] : "NULL") << endl;
    }
    cout << endl;
    return 0;
}

void createTable(sqlite3* db) {
    const char* sql = "CREATE TABLE IF NOT EXISTS STUDENT ("
                      "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                      "NAME TEXT NOT NULL,"
                      "AGE INT NOT NULL,"
                      "DEPARTMENT TEXT NOT NULL);";
    char* errMsg = 0;
    int rc = sqlite3_exec(db, sql, 0, 0, &errMsg);

    if (rc != SQLITE_OK) {
        cout << "Error creating table: " << errMsg << endl;
        sqlite3_free(errMsg);
    } else {
        cout << "Table created successfully.\n";
    }
}

void insertStudent(sqlite3* db) {
    string name, department;
    int age;
    cout << "Enter name: ";
    getline(cin, name);
    cout << "Enter age: ";
    cin >> age;
    cin.ignore();
    cout << "Enter department: ";
    getline(cin, department);

    string sql = "INSERT INTO STUDENT (NAME, AGE, DEPARTMENT) VALUES ('" + name + "', " + to_string(age) + ", '" + department + "');";
    char* errMsg = 0;
    int rc = sqlite3_exec(db, sql.c_str(), 0, 0, &errMsg);

    if (rc != SQLITE_OK) {
        cout << "Error inserting data: " << errMsg << endl;
        sqlite3_free(errMsg);
    } else {
        cout << "Student added successfully.\n";
    }
}

void viewStudents(sqlite3* db) {
    const char* sql = "SELECT * FROM STUDENT;";
    char* errMsg = 0;
    int rc = sqlite3_exec(db, sql, callback, 0, &errMsg);

    if (rc != SQLITE_OK) {
        cout << "Error retrieving data: " << errMsg << endl;
        sqlite3_free(errMsg);
    }
}

void deleteStudent(sqlite3* db) {
    int id;
    cout << "Enter student ID to delete: ";
    cin >> id;
    cin.ignore();

    string sql = "DELETE FROM STUDENT WHERE ID = " + to_string(id) + ";";
    char* errMsg = 0;
    int rc = sqlite3_exec(db, sql.c_str(), 0, 0, &errMsg);

    if (rc != SQLITE_OK) {
        cout << "Error deleting data: " << errMsg << endl;
        sqlite3_free(errMsg);
    } else {
        cout << "Student deleted successfully.\n";
    }
}

int main() {
    sqlite3* db;
    int rc = sqlite3_open("student.db", &db);

    if (rc) {
        cout << "Can't open database: " << sqlite3_errmsg(db) << endl;
        return 0;
    } else {
        cout << "Opened database successfully.\n";
    }

    createTable(db);

    int choice;
    do {
        cout << "\n1. Add Student\n2. View Students\n3. Delete Student\n4. Exit\nChoose an option: ";
        cin >> choice;
        cin.ignore();

        switch (choice) {
            case 1:
                insertStudent(db);
                break;
            case 2:
                viewStudents(db);
                break;
            case 3:
                deleteStudent(db);
                break;
            case 4:
                cout << "Exiting...\n";
                break;
            default:
                cout << "Invalid choice!\n";
        }
    } while (choice != 4);

    sqlite3_close(db);
    return 0;
}
