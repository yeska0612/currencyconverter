class FdCategories extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `<section class="categories">
            <h2>Categories</h2>
            <fd-category border="true" src="https://picsum.photos/32/32?random=1" title="Food1"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=2" title="Food2"></fd-category>
            <fd-category border="true" src="https://picsum.photos/32/32?random=3" title="Food3"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=4" title="Food4"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=5" title="Food5"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=6" title="Food6"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=7" title="Food7"></fd-category>
            <fd-category src="https://picsum.photos/32/32?random=8" title="Food8"></fd-category>
        </section>`;
    }

}

window.customElements.define('fd-categories', FdCategories);