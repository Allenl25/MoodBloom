let selectedMood = null

document.querySelectorAll(".mood-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        selectedMood = btn.getAttribute("data-mood");

        document.querySelectorAll(".mood-btn").forEach(b => b.classList.remove("selected"));
        btn.classList.add("selected");
    });
});

document.querySelector(".mood-save-btn").addEventListener("click", async () => {
    const note = document.getElementById("notes").value;

    if (!selectedMood) {
        alert("Please select a mood before saving");
        return;
    }

    const response = await fetch("/api/mood", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            mood: selectedMood,
            note: note
        })
    });

    if (response.ok) {
        alert("Mood saved");
        document.getElementById("notes").value = "";
        selectedMood = null;
        document.querySelectorAll(".mood-btn").forEach(b => b.classList.remove("selected"));

        window.location.reload();
    } else {
        alert("Error saving mood");
    }
});

document.querySelectorAll(".mood-edit-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        document.getElementById("editEntryId").value = btn.dataset.id;
        document.getElementById("editMood").value = btn.dataset.mood;
        document.getElementById("editNotes").value = btn.dataset.note;

        document.getElementById("editModal").style.display = "flex";
    });
});

document.getElementById("saveEditBtn").addEventListener("click", async () => {
    const id = document.getElementById("editEntryId").value;
    const mood = document.getElementById("editMood").value;
    const note = document.getElementById("editNotes").value;

    const response = await fetch(`/api/mood/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ mood, note })
    });

    if (response.ok) {
        location.reload();
    }
});

const modal = document.getElementById("editModal");
const modalBox = modal.querySelector(".modal-box");

modal.addEventListener("mousedown", (e) => {
    modal.dataset.outsideClick = !modalBox.contains(e.target);
});

modal.addEventListener("mouseup", (e) => {
    if (modal.dataset.outsideClick === "true" && !modalBox.contains(e.target)) {
        modal.style.display = "none";
    }
});

document.getElementById("closeEditBtn").addEventListener("click", () => {
    modal.style.display = "none";
});

document.querySelectorAll(".mood-delete-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        document.getElementById("moodDeleteEntryId").value = btn.dataset.id;
        document.getElementById("moodDeleteModal").style.display = "flex";
    });
});

document.getElementById("confirmDeleteBtn").addEventListener("click", async () => {
    const id = document.getElementById("moodDeleteEntryId").value;

    const response = await fetch(`/api/mood/${id}/delete`, {
        method: "PUT"
    });

    if (response.ok) {
        location.reload();
    }
});

const deleteModal = document.getElementById("moodDeleteModal");
const deleteModalBox = deleteModal.querySelector(".modal-box");

deleteModal.addEventListener("mousedown", (e) => {
    deleteModal.dataset.outsideClick = !deleteModalBox.contains(e.target);
});

deleteModal.addEventListener("mouseup", (e) => {
    if (deleteModal.dataset.outsideClick === "true" && !deleteModalBox.contains(e.target)) {
        deleteModal.style.display = "none";
    }
});

document.getElementById("cancelDeleteBtn").addEventListener("click", () => {
    deleteModal.style.display = "none";
});