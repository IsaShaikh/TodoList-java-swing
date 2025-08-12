# 📝 Modern ToDo - Sleek Java Swing Application

A fresh take on the classic to-do list, built entirely with **Java Swing**.  
Forget the outdated, clunky look—this app delivers a **sleek dark theme**, **smooth animations**, and **intuitive task management**, proving that Swing can still shine in 2025.

---

## ✨ Features

| Icon | Feature | Description |
|------|---------|-------------|
| 🎨 | **Modern Dark UI** | Custom dark theme that replaces the default Swing look. Clean, professional, and easy on the eyes. |
| ✏️ | **In-Place Editing** | Edit tasks directly in the list—no disruptive pop-up windows. |
| ✅ | **Task Management** | Full **CRUD**: Create, Read, Update, Delete tasks. Mark tasks complete with a strikethrough. |
| 💾 | **Automatic Saving** | Tasks are saved automatically to `.todo_tasks.txt` in your home folder. |
| 🔍 | **Live Search Filter** | Filter tasks instantly as you type in the search bar. |
| 📂 | **Export to CSV** | Save your tasks with their status in `.csv` format from the File menu. |
| 🧹 | **Clear Completed** | Remove all completed tasks in one click. |
| 📊 | **Status Counter** | Displays completed vs total tasks. |
| 🎬 | **Smooth Animations** | Fade-in and fade-out animations when adding or deleting tasks. |

---

## 🛠 Built With

- **Java 8+**
- **Java Swing** – UI framework
- **Model-View-Controller (MVC)** architecture for clean, scalable code
- **UIConstants Design System** – centralized styling for colors and fonts

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK) 8 or newer** installed.

---

### Installation & Running

#### **Clone the Repository**
```bash
git clone https://github.com/YourUsername/YourRepoName.git
```
#### Run from an IDE (Recommended)
1. Open the project in IntelliJ IDEA, Eclipse, or your preferred IDE.

2. Locate Main.java.

3. Run the file — the IDE will handle compilation.

### 📂 Project Structure
```aiignore
src/
├── Main.java                  # Entry point
└── com/
    └── todoapp/
        ├── controller/
        │   └── ToDoController.java   # Handles user input & updates model/view
        ├── model/
        │   ├── Task.java              # Task object representation
        │   └── ToDoModel.java         # Task list management & persistence
        └── ui/
            ├── TaskPanel.java         # UI for a single task
            ├── ToDoView.java          # Main application window
            └── UIConstants.java       # Centralized colors, fonts, styles

```

---
## 📸 Example Output
Below is an example of Swing todo Application (With All features Covered):

[Watch Demo](images/output.mp4)


---
## 🔮 Future Enhancements

I am open to suggestions and plan to continue developing this platform.

---

## 🤝 Contact

### 🧑‍💻 Isa Shaikh - [isashaikh2005@gmail.com](mailto:isashaikh2005@gmail.com)

### 🔗Project Link: [Swing-Based-Todo-App](https://github.com/IsaShaikh/TodoSwingApp)