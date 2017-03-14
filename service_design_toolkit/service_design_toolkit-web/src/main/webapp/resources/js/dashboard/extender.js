function extender() {
            this.cfg.axes.xaxis.tickOptions.showGridline = false;
            this.cfg.axes.yaxis.tickOptions.showGridline = false;
            this.cfg.axes.xaxis.label = false;
            this.cfg.canvasOverlay = {
                show: true,
                objects: [{horizontalLine:{
                shadow: false,
                lineWidth:1,
                xOffset: 0,
                color:"rgb(0, 0, 0)",
                y:3
            }}]}
        }
  