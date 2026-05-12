class ShApp extends HTMLElement {
    constructor() {
        super();
        //implementation
    }

    render() {
        this.innerHTML = /*html*/`
            <header><h1>Shop</h1></header>
            <main>
                <h2>Products</h2>
                <sh-products></sh-products>
            </main>
            <aside>
                <h2>Cart</h2>
                <sh-cart></sh-cart>
            </aside>
            <footer></footer>
        `;
    }
    connectedCallback() {
        this.render();
    }

    disconnectedCallback() {
        //implementation
    }

    attributeChangedCallback(name, oldVal, newVal) {
        //implementation
    }

    adoptedCallback() {
        //implementation
    }

}

window.customElements.define('sh-app', ShApp);