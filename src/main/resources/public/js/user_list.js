$(() => {
    $(".btn-edit").on("click", function () {
        let id = $(this).attr("data-id");
        let login = $(this).attr("data-login");
        let firstName = $(this).attr("data-firstname");
        let lastName = $(this).attr("data-lastname");
        openEditPopup(id, login, firstName, lastName);
    });
    $(".btn-delete").on("click", function () {
        deleteUser($(this).attr("data-id"));
    });
    $(".btn-unblock").on("click", function () {
        unblockUser($(this).attr("data-id"));
    });
    $(".btn-block").on("click", function () {
        blockUser($(this).attr("data-id"));
    });
    $('.popup-background, .close-popup').on("click", closePopup);

    $(document).on("click", ".save-user", function (e) {
        e.preventDefault();
        closePopup();
        $.ajax("/admin/update-user", {
            dataType: 'json',
            method: 'POST',
            data: {
                id: $("#userId").val(),
                firstName: $("#firstName").val(),
                lastName: $("#lastName").val(),
                userLogin: $("#userLogin").val(),
            }
        }).then(
            (data) => {
                // openPopup(data);
                // setTimeout(() => {
                window.location.reload();
                // }, 1500)
            },
            (data) => {
                window.location.reload();
            }
        );
    });
});

function openEditPopup(id, login, firstName, lastName) {
    let html =
        '<div class="form-wrapper form__profile"> ' +
        '<div class="close-icon"> ' +
        '<i class="close-popup fas fa-times"></i> ' +
        '</div> ' +
        '<h1 class="form__header">Edit user </h1> ' +
        '<form id="profileForm" class="form"> ' +
        '<input id="userId" type="hidden" value="' + id + '"> ' +
        '<fieldset class="form__group"> ' +
        '<label for="firstName">' +
        '<span class="label__icon fa fa-user"></span>' +
        '</label> ' +
        '<input id="firstName" ' +
        'name="firstName" ' +
        'class="form__element" ' +
        'value="' + firstName + '"' +
        'type="text" ' +
        'placeholder="" ' +
        'required/> ' +
        '</fieldset> ' +
        '<fieldset class="form__group"> ' +
        '<label for="lastName">' +
        '<span class="label__icon fa fa-user"></span>' +
        '</label> ' +
        '<input id="lastName" ' +
        'class="form__element" ' +
        'type="text"' +
        'value="' + lastName + '" ' +
        'placeholder="" required/> ' +
        '</fieldset> ' +
        '<fieldset class="form__group"> ' +
        '<label for="userLogin">' +
        '<span class="label__icon fa fa-at"></span>' +
        '</label> ' +
        '<input id="userLogin" ' +
        'name="login" ' +
        'class="form__element" ' +
        'type="text" ' +
        'value="' + login + '" ' +
        'placeholder=""required/> ' +
        '</fieldset> ' +
        '<fieldset class="form__group"> ' +
        '<div class="form__button save-user"> Save</div> ' +
        '</fieldset> ' +
        '</form> ' +
        '</div>';

    openPopup(html);

    // $.ajax("/user", {
    //     dataType: 'json',
    //     method: 'POST',
    //     data: {
    //         id: id,
    //     },
    //     success: (data) => {
    //         populateForm(data);
    //     },
    // });
    // $('body').addClass('popup-opened');
}

// function populateForm(data) {
//     $("#userId").val(data.id);
//     $("#firstName").val(data.firstName);
//     $("#lastName").val(data.lastName);
//     $("#userLogin").val(data.login);
// }

function deleteUser(id) {
    $.ajax("/admin/delete-user/" + id).then(
        (data) => {
            window.location.reload();
        },
        (data) => {
            window.location.reload();
        }
    );
}

function unblockUser(id) {
    $.ajax("/admin/unblock-user/" + id).then(
        (data) => {
            window.location.reload();
        },

        (data) => {
            window.location.reload();
        },
    );
}

function blockUser(id) {
    $.ajax("/admin/block-user/" + id).then(
        (data) => {
            window.location.reload();
        },

        (data) => {
            window.location.reload();
        },
    );

}

function closePopup() {
    $('body').removeClass('popup-opened');
}