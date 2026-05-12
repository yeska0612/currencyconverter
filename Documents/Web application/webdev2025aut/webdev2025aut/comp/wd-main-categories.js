class WdMainCategories extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
        <section class="categories">
            <h2>Categories</h2>
            <wd-category cname="Category 1" pic="https://picsum.photos/64/64?random=3"></wd-category>
            <wd-category cname="Category 2" ></wd-category>
            <wd-category cname="Category 4" pic="https://picsum.photos/64/64?random=5"></wd-category>
            <wd-category cname="Category 3" ></wd-category>
            <wd-category pic="https://picsum.photos/64/64?random=7"></wd-category>
            <wd-category cname="Category 0" pic="https://picsum.photos/64/64?random=8"></wd-category>
            <wd-category cname="Category"></wd-category>
            <wd-category cname="Category"></wd-category>
            <wd-category cname="Category"></wd-category>
            <wd-category ></wd-category>
</section>`;
    }



}

window.customElements.define('wd-main-categories', WdMainCategories);