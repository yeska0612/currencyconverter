class WdCategory extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        const catName = this.getAttribute('cname') || "No category";
        const pic = this.getAttribute('pic') || "https://picsum.photos/64/64";
        this.innerHTML = `
        <article>
            <img src="${pic}" alt="">
            <h3>${catName}</h3>
        </article> `;
    }

}

window.customElements.define('wd-category', WdCategory);