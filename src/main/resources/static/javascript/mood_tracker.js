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
    } else {
        alert("Error saving mood");
    }
});