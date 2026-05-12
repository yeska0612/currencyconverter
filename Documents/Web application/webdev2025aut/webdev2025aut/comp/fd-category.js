class FdCategory extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        const hasBorder = this.getAttribute('border') === 'true';
        this.innerHTML = `
        <article ${ hasBorder?'style="border:1px solid #ccc;"':""}>
            <img src="${this.getAttribute('src')}" alt="${this.getAttribute('title')}">
            <h3>${this.getAttribute('title')}</h3>
        </article> `;
    }
}

window.customElements.define('fd-category', FdCategory);