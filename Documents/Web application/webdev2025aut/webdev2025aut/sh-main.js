class ShMain extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        const productId=this.getAttribute('pid') || '';
        const productName=this.getAttribute('pname') || 'Nergui baraa';
        this.innerHTML = `
            <h2>Main Component - Product ID:${productId}</h2>
            <img src="https://via.placeholder.com/300" alt="Product Image">
            <p>This is a ${productName} description for product ID </p>
            <button>Add to Cart</button>`;
    }


}

window.customElements.define('sh-main', ShMain);