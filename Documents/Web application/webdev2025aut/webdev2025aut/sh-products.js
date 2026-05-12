class ShProducts extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.render();
    }

    render() {
        this.innerHTML = /*html*/`
        <input type="text">
        <input type="search" name="" id="">
        <button>Search</button>
        <sh-product name="Product 1" img="https://placehold.net/product-600x400.png" price="1,000,000"></sh-product>
        <sh-product name="Product 2" img="https://placehold.net/product-600x400.png" price="2,000,000"></sh-product>
        <sh-product name="Product 3" price="3,000,000"></sh-product>
        `;
    }

    disconnectedCallback() {
    }

    attributeChangedCallback(name, oldVal, newVal) {
    }

    adoptedCallback() {
    }

}

window.customElements.define('sh-products', ShProducts);