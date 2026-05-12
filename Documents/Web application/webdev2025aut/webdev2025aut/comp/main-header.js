class MainHeader extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
        <header>
            <div>
                <input type="search" name="" id="">
                <button>🔍</button>
            </div>
            <div>🌐
                <button>Profile</button>
            </div>
        </header>`;
       
    }

}

window.customElements.define('main-header', MainHeader);