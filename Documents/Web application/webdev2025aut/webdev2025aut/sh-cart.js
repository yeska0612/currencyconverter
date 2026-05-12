class ShCart extends HTMLElement {
    constructor() {
        super();
        this.cartItems = new Map();
    }
    connectedCallback() {
        this.render();
    }
    render() {
        if (this.cartItems.size === 0) {
            this.innerHTML = `<p>Your cart is empty.</p>`;
            return;
        }

        let preparedHTML = "";
        this.cartItems.forEach(p => {

            preparedHTML += /*html*/`
                <sh-product mode="compact" 
                    name="${p.name}" 
                    img="${p.img}" 
                    price="${p.price}"
                    qty="${p.qty}">
                </sh-product>`;
        });
        this.innerHTML = preparedHTML;
    }
    addProduct(product) {
        console.log("Calling addProduct", product);
        console.log("product name=", product.name);
        if (this.cartItems.has(product.name)) {
            this.cartItems
                .get(product.name)
                .qty += 1;
            this.render();
            return;
        }

        product.qty = 1;
        this.cartItems.set(product.name, product);
        // this.querySelector(`sh-product[name='${product.name}'] .qty`).textContent = product.qty;
        this.render();

    }
    removeProduct(productName) { }
}

window.customElements.define('sh-cart', ShCart);