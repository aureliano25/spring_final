$(() => {
    $('.filter-checkbox').on('click', function () {
        let subjectId = $(this).attr("data-id");
        removePageFromUrl();
        $(this).is(":checked")
            ? addToUrl("subjects", subjectId)
            : removeFromUrl("subjects", subjectId);
    });

    $('.page-link').on('click', function () {
        let page = $(this).attr("data-page");
        if (page !== undefined ) {
            updateUrlParameter("page", page);
        }
    });

    $('.select-sort').on('click', function () {
        $(this).toggleClass('opened');
    })

    $('.sort-item').on('click', function () {
        removePageFromUrl();
        updateUrlParameter('sort', $(this).data('sort'));
    });

    $('.sort_name').text($('.sort-item-current').text());
});

const CSRF_FIELD_SELECTOR = "#_csrf";

function removeFromUrl(parameterName, valueToRemove) {
    let values = getValuesListFromUrl(parameterName);
    let index = values.indexOf(valueToRemove);
    values.splice(index, 1);
    updateUrlParameter(parameterName, values.join(','));
}

function addToUrl(parameterName, valueToAdd) {
    let values = getValuesListFromUrl(parameterName);
    if (values.includes(valueToAdd)) {
        return;
    }

    values.push(valueToAdd);
    updateUrlParameter(parameterName, values.sort().join(','));
}

function getValuesListFromUrl(parameterName) {
    let url = new URL(window.location.href);
    let parameterValue = url.searchParams.get(parameterName);
    let valuesList = [];
    if (parameterValue !== null) {
        valuesList = parameterValue.split(',');
    }

    return valuesList;
}

function updateUrlParameter(parameterName, value) {
    let url = new URL(window.location.href);
    url.searchParams.set(parameterName, value);
    if (value === '') {
        url.searchParams.delete(parameterName);
    }

    window.location.href = url.toString();
}

function removePageFromUrl() {
    let url = new URL(window.location.href);
    url.searchParams.delete("page");
    window.history.pushState('', '', url.toString());
}

function getCsrfInput() {
    let tokenFieldName = $(CSRF_FIELD_SELECTOR).attr("name");
    let tokenValue = $(CSRF_FIELD_SELECTOR).attr("content");

    return `<input type="hidden" name="${tokenFieldName}" value="${tokenValue}" />`;
}

function getCsrfFieldName() {
    return $(CSRF_FIELD_SELECTOR).attr("name");
}

function getCsrfValue() {
    return $(CSRF_FIELD_SELECTOR).attr("content");
}