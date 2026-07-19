document.addEventListener('DOMContentLoaded', function () {

    const titleField = document.getElementById('journal-title');
    const contentField = document.getElementById('journal-content');
    const editBtn = document.getElementById('edit-entry-btn');
    const saveBtn = document.getElementById('save-entry-btn');

    const isSavedEntry = titleField.hasAttribute('readonly');

    function setEditingMode(editing) {
        titleField.readOnly = !editing;
        contentField.readOnly = !editing;
        saveBtn.disabled = !editing;
        editBtn.disabled = editing;
    }

    if (isSavedEntry) {

        setEditingMode(false);

        editBtn.addEventListener('click', function () {
            setEditingMode(true);
            titleField.focus();
        });
    }
});
