document.querySelectorAll(".toggle[aria-controls]").forEach(el => {
    const controls = document.getElementById(el.getAttribute("aria-controls"));
    const initialState = controls.classList.contains("collapsed");
    el.classList.toggle("collapsed", initialState);
    el.setAttribute("aria-checked", (!initialState).toString());
    el.addEventListener("click", e => {
        const collapsed = controls.classList.toggle("collapsed");
        el.setAttribute("aria-checked", (!collapsed).toString());
    })
});
