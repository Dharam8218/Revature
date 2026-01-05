document.addEventListener("DOMContentLoaded", function () {
  let quizData = null;
  fetch("quiz-data.json")
    .then((response) => response.json())
    .then((data) => {
      quizData = data;
      initSections();
    })
    .catch((error) => console.error("Error loading quiz data:", error));

  function initSections() {
    const sections = document.querySelectorAll(".section");
    sections.forEach((section) => {
      section.addEventListener("click", () => {
        let sectionNumber = parseInt(section.getAttribute("data-section"));
        startQuiz(sectionNumber);
      });
    });
  }

  function startQuiz(sectionIndex) {
    const currentQuestions = quizData.sections[sectionIndex].questions;
    let currentQuestionIndex = 0;
    let score = 0;
    let answerSelected = false;
    const quizContainer = document.getElementById("quiz-container");
    const questionContainer = document.getElementById("question-container");

    quizContainer.style.display = "none";
    questionContainer.style.display = "block";

    questionContainer.innerHTML = `
           <button id="home-button">Home</button>
            <p id="max-score" style="margin: 0;">Max Score: ${currentQuestions.length}</p>
        <p id="score">Score: 0</p>
        <div id="question"></div>
        <div id="options"></div>
        <button id="next-button">Next</button>
      `;

    document
      .getElementById("home-button")
      .addEventListener("click", function () {
        document.getElementById("quiz-container").style.display = "grid";
        document.getElementById("question-container").style.display = "none";
      });

    showQuestions();

    function showQuestions() {
      const question = currentQuestions[currentQuestionIndex];
      const questionElement = document.getElementById("question");
      const optionsElement = document.getElementById("options");

      questionElement.textContent = question.question;
      optionsElement.innerHTML = " ";

      if (question.questionType === "MCQ") {
        question.options.forEach((option) => {
          const optionElement = document.createElement("div");
          optionElement.textContent = option;
          optionElement.addEventListener("click", function () {
            if (!answerSelected) {
              answerSelected = true;
              optionElement.classList.add("selected");
              checkAnswer(option, question.answer);
            }
          });
          optionsElement.appendChild(optionElement);
        });
      } else {
        const inputElement = document.createElement("input");
        inputElement.type =
          question.questionType === "number" ? "number" : "text";
        inputElement.type =
          question.questionType === "number" ? "number" : "text";
        inputElement.placeholder =
          question.questionType === "number" ? "Enter number" : "Enter text";
        const submitButton = document.createElement("button");
        submitButton.textContent = "Submit Answer";
        submitButton.className = "submit-answer";

        submitButton.onclick = () => {
          if (!answerSelected) {
            answerSelected = true;
            checkAnswer(
              inputElement.value.toString(),
              question.answer.toString()
            );
          }
        };

        optionsElement.appendChild(inputElement);
        optionsElement.appendChild(submitButton);
      }
    }
    function checkAnswer(givenAnswer, correctAnswer) {
      const feedbackElement = document.createElement("div");
      feedbackElement.id = "feedback";
      if (
        givenAnswer === correctAnswer ||
        givenAnswer.toLowerCase() === correctAnswer.toLowerCase()
      ) {
        score++;
        feedbackElement.textContent = "Correct";
        feedbackElement.style.color = "green";
      } else {
        feedbackElement.textContent = `Wrong! Correct answer: ${correctAnswer}`;
        feedbackElement.style.color = "red";
      }

      const optionsElement = document.getElementById("options");
      optionsElement.appendChild(feedbackElement);
      updateScore();
    }

    function updateScore() {
      document.getElementById("score").textContent = "Score:" + score;
    }

    document.getElementById("next-button").addEventListener("click", () => {
      if (currentQuestionIndex >= currentQuestions.length - 1) {
        console.log("Quiz Over!!!");
        endQuiz();
      } else {
        answerSelected = false;
        currentQuestionIndex++;
        showQuestions();
      }
    });

    function endQuiz() {
      questionContainer.innerHTML = `
              <h1>Quiz Completed!</h1>
              <p>Your Final Score: ${score}/${currentQuestions.length}</p>
              <button id="home-button">Go to Home</button>
           `;

      document
        .getElementById("home-button")
        .addEventListener("click", function () {
          quizContainer.style.display = "grid";
          questionContainer.style.display = "none";
        });
    }
  }
});

/*
document.addEventListener("DOMContentLoaded", function () {
    let quizData = null;
    fetch("quiz-data.json")
      .then((response) => response.json())
      .then((data) => {
        quizData = data;
        initSections();
      })
      .catch((error) => console.error("Error loading quiz data:", error));
  
    function initSections() {
      const sections = document.querySelectorAll(".section");
      sections.forEach((section) => {
        section.addEventListener("click", () => {
          let sectionNumber = parseInt(section.getAttribute("data-section"));
          startQuiz(sectionNumber);
        });
      });
    }
  
    function startQuiz(sectionIndex) {
      const currentQuestions = quizData.sections[sectionIndex].questions;
      let currentQuestionIndex = 0;
      let score = 0;
      let answerSelected = false;
  
      const quizContainer = document.getElementById("quiz-container");
      const questionContainer = document.getElementById("question-container");
  
      quizContainer.style.display = "none";
      questionContainer.style.display = "block";
  
      questionContainer.innerHTML = `
        <p id="score">Score: 0</p>
        <div id="question"></div>
        <div id="options"></div>
        <button id="next-button">Next</button>
      `;
  
      showQuestion();
  
      function showQuestion() {
        const question = currentQuestions[currentQuestionIndex];
        const questionElement = document.getElementById("question");
        const optionsElement = document.getElementById("options");
  
        questionElement.textContent = question.question;
        optionsElement.innerHTML = "";
  
        if (question.questionType === "MCQ") {
          question.options.forEach((option) => {
            const optionElement = document.createElement("div");
            optionElement.textContent = option;
            optionElement.className = "option";
            optionElement.addEventListener("click", function () {
              if (!answerSelected) {
                answerSelected = true;
                optionElement.classList.add("selected");
                checkAnswer(option, question.answer);
              }
            });
            optionsElement.appendChild(optionElement);
          });
        } else {
          const inputElement = document.createElement("input");
          inputElement.type =
            question.questionType === "number" ? "number" : "text";
          inputElement.placeholder =
            question.questionType === "number" ? "Enter number" : "Enter text";
  
          const submitButton = document.createElement("button");
          submitButton.textContent = "Submit Answer";
          submitButton.className = "submit-answer";
  
          submitButton.onclick = () => {
            if (!answerSelected) {
              answerSelected = true;
              checkAnswer(
                inputElement.value.toString(),
                question.answer.toString()
              );
            }
          };
  
          optionsElement.appendChild(inputElement);
          optionsElement.appendChild(submitButton);
        }
      }
  
      function checkAnswer(givenAnswer, correctAnswer) {
        const feedbackElement = document.createElement("div");
        feedbackElement.id = "feedback";
  
        if (
          givenAnswer === correctAnswer ||
          givenAnswer.toLowerCase() === correctAnswer.toLowerCase()
        ) {
          score++;
          feedbackElement.textContent = "Correct!";
          feedbackElement.style.color = "green";
        } else {
          feedbackElement.textContent = `Wrong! Correct answer: ${correctAnswer}`;
          feedbackElement.style.color = "red";
        }
  
        const optionElement = document.getElementById("options");
        optionElement.appendChild(feedbackElement);
        updateScore();
      }
  
      function updateScore() {
        document.getElementById("score").textContent = `Score: ${score}`;
      }
  
      document.getElementById("next-button").addEventListener("click", () => {
        if (currentQuestionIndex >= currentQuestions.length - 1) {
          endQuiz();
        } else {
          answerSelected = false;
          currentQuestionIndex++;
          showQuestion();
        }
      });
  
      function endQuiz() {
        questionContainer.innerHTML = `
          <h1>Quiz Completed!</h1>
          <p>Your Final Score: ${score}/${currentQuestions.length}</p>
          <button id="home-button">Go to Home</button>
        `;
  
        document
          .getElementById("home-button")
          .addEventListener("click", function () {
            quizContainer.style.display = "grid";
            questionContainer.style.display = "none";
          });
      }
    }
  });
  
*/
