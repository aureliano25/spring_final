$(() => {
    $('.subject-edit').on('click', function () {
        openSubjectCreationForm($(this).data('name'), $(this).data('id'));
    });

    $('.create-subject').on('click', function () {
        openSubjectCreationForm('', 0);
    })

    $('.subject-delete').on('click', function () {
        $.get("/admin/subject/delete/" + $(this).data('id')).then(
            (data) => printNoty(data),
            (data) => printNoty(data),
        );
    });

    $(document).on('submit', '.form-update', function (e) {
        e.preventDefault();
        let action = $(this).attr('action');
        let data = $(this).serialize();
        $.post(action, data)
            .then(
                (data) => {
                    window.location.reload();
                },
                (data) => {
                    window.location.reload();
                }
            );
    });
});

function openSubjectCreationForm(name, id) {
    let html = `
                <form class="form form-update text-center" action="/admin/subject/update">
                    <fieldset class="form__group">
                        <label for="subject-name">Subject name:</label>
                        <div class="empty-item"></div>
                        <input id="subject-name"
                               name="name"
                               class="form__element element_without_image"
                               type="text"
                               value="${name}"
                               required/>
                    </fieldset>
                    <input type="hidden" name="id" value="${id}">
                    ${getCsrfInput()}
                    <button class="form__button save-subject">Save</button>
                </form>`;

    openPopup(html);
}
