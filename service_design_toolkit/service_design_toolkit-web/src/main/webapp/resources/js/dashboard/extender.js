function extender() {
            this.cfg.axes.xaxis.tickOptions.showGridline = false;
            this.cfg.axes.yaxis.tickOptions.showGridline = false;
            this.cfg.canvasOverlay = {
                show: true,
                objects: [{horizontalLine:{
                shadow: false,
                lineWidth:5,
                xOffset: 0,
                color:"rgb(89, 198, 154)",
                y:3
            }}]}
        }