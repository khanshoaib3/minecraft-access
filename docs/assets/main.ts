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

const headings = Array.from(document.querySelectorAll(".content h2, .content h3")).reverse() as HTMLElement[];
const toc = document.getElementsByClassName("table-of-contents")[0];
const content = document.getElementsByClassName("content")[0];
function onScroll() {
    toc.querySelectorAll("a").forEach(el => el.classList.remove("active"));
    const heading = headings.filter(el => (el.offsetTop - el.clientHeight*3) < content.scrollTop)[0];
    if (heading === undefined) {
        return;
    }
    const tocLink = toc.querySelector(`a[href="#${heading.id}"]`) as HTMLElement;
    tocLink.classList.add("active");
    toc.scrollTo({top: tocLink.offsetTop - toc.clientHeight/2});
}
content.addEventListener("scroll", onScroll);
onScroll();
