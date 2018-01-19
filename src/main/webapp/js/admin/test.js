// 1.创建一个组件构造器
var myComponent = Vue.extend({
    template: '<div>This is my first component!</div>'
})

// 2.注册组件，并指定组件的标签，组件的HTML标签为<my-component>
Vue.component('my-component', myComponent)

