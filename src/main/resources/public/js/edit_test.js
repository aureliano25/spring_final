$(".info-title").on("click", function () {
    $(this).parent(".info-block").toggleClass("collapsed");
});

$(".question-edit").on("click", function () {
    openQuestionForm($(this).attr("data-id"));
});

$('.question-delete').on('click', function () {
    deleteQuestion($(this).attr('data-id'));
});

$(".add-option").on("click", addAnswer);
$(document).on("click", '.settings-save', saveQuizSettings);
$(document).on("click", '.question-save', saveQuestion);
$('.question-save').on('click', saveQuestion);

$('.create-question').on('click', () => {
    openCreateQuestionPopup();
});

$(document).on('click', '.question-create', () => {
    createQuestion();
});

let itemsToRemove = [];

$(document).on("click", ".delete-btn", function () {
    let answerId = $(this).attr("data-id");
    if (answerId !== undefined) {
        itemsToRemove.push(answerId);
    }
    $(this).parent().remove();
    // removeAnswer($(this).attr("data-id"));
});

function openQuestionForm(questionId) {
    $.ajax("/admin/question/edit?id=" + questionId)
        .then((data) => {
            openPopup(data);
        })
}

function addAnswer() {
    let answerHtml = `
        <fieldset class="form__group flex answer" id="0">
            <label class="correct_label">
                <input class="is_right" type="checkbox">Correct option 
            </label>
            <input name="answer_text"
                class="form__element element_without_image"
                type="text"
                placeholder="Option text"
                required/> 
            <div class="user-btn delete-btn" data-new="true" title="Delete answer"> 
                <i class="fas fa-minus-circle"></i> 
            </div>
        </fieldset>
    `;

    $('.answers').append(answerHtml);
}

function removeAnswer(answerId) {
    console.log("remove answer " + answerId);
}

function saveQuizSettings() {
    let data = {
        id: $("#quiz_id").val(),
        title: $("#quiz_title").val(),
        subject: $("#quiz_subject").val(),
        difficulty: $("#quiz_difficulty").val(),
        time: $("#quiz_time").val(),
    };
    data[getCsrfFieldName()] = getCsrfValue();

    $.ajax("/admin/test/edit", {
        method: 'POST',
        data: data,
    }).then(
        (data) => {
            alert(data.message);
        },
        (error) => {
            console.log(error);
        }
    );
}

function createQuestion() {
    let data = getQuestionInfo();
    data[getCsrfFieldName()] = getCsrfValue();

    $.ajax("/admin/question/create", {
        method: 'POST',
        contentType:"application/json; charset=utf-8",
        dataType:"json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(getCsrfHeaderName(), getCsrfValue());
        },
        data: JSON.stringify(data),
    }).then(
        (data) => {
            window.location.reload();
        },
        (data) => {
            window.location.reload();
        }
    );
}

function saveQuestion() {
    let question = getQuestionInfo();
    question.questionId = $("#question-id").val();

    $.ajax("/admin/question/edit", {
        method: 'POST',
        contentType:"application/json; charset=utf-8",
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(getCsrfHeaderName(), getCsrfValue());
        },
        data: JSON.stringify(question),
    }).then(
        () => {
            window.location.reload();
        },
        () => {
            window.location.reload();
        }
    );
}

function getQuestionInfo() {
    let question = {};
    question.questionText = $("#question-text").val();
    question.testId = $("#quiz_id").val();
    question.questionId = $("#question-id").val();
    question.answers = [];
    $('.answer').each(function () {
        let text = $(this).find("input[name='answer_text']").val();
        let isCorrect = $(this).find(".is_right").is(":checked");
        question.answers.push({
            id: $(this).attr("data-id"),
            text: text,
            isCorrect: isCorrect,
        });
    });
    // question.answers = JSON.stringify(question.answers);

    return question;
}

function openCreateQuestionPopup() {
    console.log("createquestionPopup");
    $.ajax("/admin/question/create")
        .then(
            (data) => {
                openPopup(data);
            },
            (data) => {
                openPopup(data);
            }
        );
}

function deleteQuestion(questionId) {
    $.ajax('/admin/question/delete', {
        method: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(getCsrfHeaderName(), getCsrfValue());
        },
        data: {
            id: questionId,
        },
    }).then(
        (data) => {
            printNoty(data)
        },
        (data) => {
            printNoty('<div class="noty-body error">Something goes wrong</div>')
        },
    );
}
