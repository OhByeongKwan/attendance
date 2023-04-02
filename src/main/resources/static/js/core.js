//기본 메세지창
let Dialog = {
    okCallback: null,
    cancelCallback: null,
    data: {},
    modal: null,
    init: function() {
        $("body .container").append(`<div id="dialog" class="modal show" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title"></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button id="dialogCancel" type="button" class="btn btn-sm btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button id="dialogOk" type="button" class="btn btn-sm btn-primary">확인</button>
                    </div>
                </div>
            </div>
        </div>`);

        Dialog.modal = new bootstrap.Modal(document.getElementById('dialog'),{});

        $('#dialogOk').on('click', function() {
            Dialog.hide();
            if (Utils.isValid(Dialog.okCallback)) {
                Dialog.okCallback(Dialog.data);
                Dialog.okCallback = null;
            }
            if (Utils.isValid(Dialog.cancelCallback)) {
                Dialog.cancelCallback = null;
            }
        });

        $('#dialogCancel').on('click', function() {
            Dialog.hide();
            if (Utils.isValid(Dialog.okCallback)) {
                Dialog.okCallback = null;
            }
            if (Utils.isValid(Dialog.cancelCallback)) {
                Dialog.cancelCallback(Dialog.data);
                Dialog.cancelCallback = null;
            }
        });

    },
    alert: function(msg, okCallback, data) {
        Dialog.okCallback = okCallback;
        Dialog.cancelCallback = okCallback;
        Dialog.data = data;

        $('#dialog .modal-body').html(`<div>${msg}</div>`);
        Dialog.title('');
        $('#dialogCancel').hide();
        Dialog.show();
        return Dialog;
    },
    confirm: function(msg, okCallback, cancelCallback, data) {
        Dialog.okCallback = okCallback;
        Dialog.cancelCallback = cancelCallback;
        Dialog.data = data;

        Dialog.title('');
        $('#dialog .modal-body').html(`<div>${msg}</div>`);
        $('#dialogCancel').show();
        Dialog.show();
        return Dialog;
    },
    title: function(title){
        $('#dialog .modal-title').html(title);
    },
    show: function() {
        Dialog.modal.show();
    },
    hide: function() {
        Dialog.modal.hide();
    }
}

//기본 유틸 목록
let Utils = {
    isValid: function(something) {
        return (something != null && something != "" && typeof something != "undefined");
    },
    isNull: function(something) {
        return !Utils.isValid(something);
    },
    formToObject: function(formIdOrFormData, option) {
        let rawFormData = formIdOrFormData;
        if(typeof rawFormData != 'object') {
            rawFormData = new FormData($(formIdOrFormData)[0]);
        }
        let data = {};

        for(let pair of rawFormData.entries()){
            if($(`input[name=${pair[0]}]`).hasClass("pass")) continue;
            let label = $('[aria-label=' + pair[0]+']').text();
            if(!Utils.isValid(pair[1]) && $(`input[name=${pair[0]}].required`).length > 0) {
                /*
                Dialog.alert(label + '을(를) 입력해주세요.', function() {
                    $(`input[name=${pair[0]}].required`).trigger('focus');
                });
                 */
                return null;
            }
            data[pair[0]] = pair[1];
        }
        return data;
    },
    getHourBetween: function(dt2, dt1) {
        let diff =(dt2.getTime() - dt1.getTime()) / 1000;
        diff /= (60 * 60);
        return Math.abs(Math.round(diff));
    },
    tryParse: function(str, defaultValue = {}) {
        try {
            return JSON.parse(str);
        } catch (e) {
            return defaultValue;
        }
    },
    formatNum: function(val) {
        if (!Utils.isValid(val)) val = 0;
        if (typeof val == "string") val = parseInt(val);
        while (/(\d+)(\d{3})/.test(val.toString())) {
            val = val.toString().replace(/(\d+)(\d{3})/, '$1' + ',' + '$2');
        }
        return val;
    },
    dateFormat: function(dateOrString, formatString) {
        if(typeof dateOrString === 'string') {
            if(dateOrString.includes('T')) {
                return Utils.dateFormat(new Date(x), 'yyyy-MM-dd hh:mm:ss');
            }
            formatString = dateOrString;
            dateOrString = new Date();
        }
        if(dateOrString instanceof Date && typeof formatString == 'undefined') {
            formatString = 'yyyy-MM-dd hh:mm:ss';
        }
        if(Utils.isNull(dateOrString)) {
            return Utils.dateFormat(new Date(), 'yyyy-MM-dd hh:mm:ss');
        }
        let z = {
            M: dateOrString.getMonth() + 1,
            d: dateOrString.getDate(),
            h: dateOrString.getHours(),
            m: dateOrString.getMinutes(),
            s: dateOrString.getSeconds(),
        };
        formatString = formatString.replace(/(M+|d+|h+|m+|s+)/g, function (v) {
            return ((v.length > 1 ? '0' : '') + eval('z.' + v.slice(-1))).slice(-2);
        });

        return formatString.replace(/(y+)/g, function (v) {
            return dateOrString.getFullYear().toString().slice(-v.length);
        });
    },
    getImage: function (path, file, type = 'food', size = 'xs') {
        // size 는 xl, md, lg 중 한 타입

        // 이미지 여러개면 첫장만
        if(typeof file == 'object' && Utils.isValid(file)) {
            file = file[0];
        }

        // TOAST 외부 이미지
        if (Utils.isValid(file) && file.includes('image.toast.com')) {
            file = file.replace('http://','https://');
            if(Utils.isValid(size)) {
                file = file.replace('.png', `_${size}.png`)
            }
            console.log(file);
            return file;
        }

        // 없으면 기본 이미지
        return `/assets/images/bg_${type}.jpg`
        /*
        if (Utils.isValid(file) && Utils.isValid(path)) {
            // 기본 이미지
            if(file.startsWith('images/') || file.startsWith('/images/')) {
                return ''.concat(Boss.Image.origin)+'/'.concat(file);
            }
        }
        else {
            switch (type) {
                case 'modal':
                    return location.host+'/images/modal_profile_default2.png';
                case 'food':
                    return ''.concat(Boss.Image.origin)+'/images/bg_food.jpg';
                case 'market':
                    return ''.concat(Boss.Image.origin)+'/images/bg_market.jpg';
                default:
                    return ''.concat(Boss.Image.origin)+'/images/bg_cafe.jpg';
            }
         */
    }
};

let AJAX = {
    get: async function(url) {
        return axios.get(url).then(function (res){return res.data;});
    },
    post: async function(url, data) {
        return this.send('post', url, data).then(function (res){return res.data;});
    },
    put: async function(url, data) {
        return this.send('put', url, data).then(function (res){return res.data;});
    },
    patch: async function(url, data) {
        return this.send('patch', url, data).then(function (res){return res.data;});
    },
    delete: async function(url, data) {
        return this.send('delete', url, data).then(function (res){return res.data;});
    },
    send: async function (method, url, data) {
        return axios({method: method, url: url, data: data});
    }
};

let Page = {
    awake: function(option = {auth: false}) {
        // 1. Dialog 생성
        Dialog.init();
        start();
        this.setDevice(option);
    },
    go: function (url, params) {
        location.href = (url);
    },

    move: function (url, params) {
        location.replace(url);
    },
    back: function () {
        //history.back();
        location.href = document.referrer;
    },
    setDevice: function(option) {
        this.setHeader(option);
        this.setGlobalNav(option);
        this.setFooter(option);
    },
    setHeader: function(option) {

    },
    setGlobalNav: function(option) {

    },
    setFooter: function(option) {

    },
    reload: function() {
        location.reload();
    }
};