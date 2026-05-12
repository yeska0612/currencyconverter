class ShProduct extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.mode=this.getAttribute("mode") ?? "default"; //default, compact, detailed
        this.price = this.getAttribute("price") ?? "Free";
        this.name = this.getAttribute("name") ?? "Unnamed Product";
        this.img = this.getAttribute("img") ?? "https://placehold.net/product-600x400.png";
        this.qty = this.getAttribute("qty") ?? 1;
        this.render();
    }

    render() { 
        switch(this.mode) {
            case "compact":
                this.renderCompact();
                break;
            case "detailed":
                //this.renderDetailed();
                break;
            case "default":
            default:
                this.renderDefault();
                break;
        }
    }
    renderDefault() {
        this.innerHTML = /*html*/`
            <article class="product-card">
                <img src="${this.img}" alt="${this.name}">
                <h3>${this.name}</h3>
                <p>Price: $${this.price}</p>
                <button>Add to Cart</button>
        </article>
        `;
        this.querySelector("button")
            .addEventListener("click", _ =>
                document
                    .querySelector("sh-cart")
                    .addProduct(this));
    }
    renderCompact() {
        this.innerHTML = /*html*/`
            <article class="product-card compact">
                <img src="${this.img}" alt="${this.name}">
                <h3>${this.name}</h3>
                <p>₮${this.price} x<span class="qty">${this.qty}</span></p>
            </article>
        `;
    }
    disconnectedCallback() {
    }

    attributeChangedCallback(name, oldVal, newVal) {
    }

    adoptedCallback() {
    }

}

window.customElements.define('sh-product', ShProduct);