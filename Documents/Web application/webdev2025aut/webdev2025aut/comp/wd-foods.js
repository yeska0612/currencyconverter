class WdFoods extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `<section class="foods">
            <article>
                <h3>Food1</h3>
                <img src="https://picsum.photos/150/100?random=1" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">1<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=1" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
                <h3>Food2</h3>
                <img src="https://picsum.photos/150/100?random=2" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">2<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=2" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
                <h3>Food3</h3>
                <img src="https://picsum.photos/150/100?random=3" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">3<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=3" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
                <h3>Food4</h3>
                <img src="https://picsum.photos/150/100?random=4" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">4<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=4" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
                <h3>Food5</h3>
                <img src="https://picsum.photos/150/100?random=5" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">5<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=5" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
                <h3>Food6</h3>
                <img src="https://picsum.photos/150/100?random=6" alt=""><input type="checkbox" name="" id="">
                <div>
                    <p class="price">6<span>5.99</span></p>
                    <div>
                        <img src="https://picsum.photos/24/24?random=6" alt="">
                        <p>Vegan</p>
                        <button>Add to dish</button><button class="btn_primary">Buy Now</button>
                    </div>
                </div>
            </article>
            <article>
        </section>`;
    }

}

window.customElements.define('wd-foods', WdFoods);