import request from "@/utils/request"

export default class TTSRecorder {
  constructor() {
    this.msgStore = null
    this.msgDom = null
  }

  start(store, msgDom) {
    this.msgStore = store
    this.msgDom = msgDom
    this.callDoubao()
  }

  async callDoubao() {
    try {
      const messages = this.msgStore.state.msg.list.map(msg => ({
        role: msg.role,
        content: msg.content
      }))

      console.log("发送到豆包的消息：", messages)

      const res = await request.post("/ai/chat", { messages })
      console.log("豆包接口返回 Result：", res)

      if (res.code !== 1000) {
        alert(res.message || "AI 服务调用失败")
        console.error("豆包调用失败：", res)
        return
      }

      const data = res.data || {}
      console.log("豆包原始 data：", data)

      // 标准豆包同步接口结构：data.choices[0].message.content
      let content = ""
      if (
        data &&
        data.choices &&
        Array.isArray(data.choices) &&
        data.choices.length > 0 &&
        data.choices[0].message &&
        typeof data.choices[0].message.content === "string"
      ) {
        content = data.choices[0].message.content
      }

      if (!content) {
        console.warn("豆包返回结构异常或无内容：", data)
        content = "AI 暂无回复内容（返回结构不符合预期）"
      }

      this.msgStore.dispatch("msg/aiAddMsg", {
        content,
        status: 2
      })

      if (this.msgDom) {
        this.msgDom.scrollTop = this.msgDom.scrollHeight + 500
      }
    } catch (e) {
      console.error("调用豆包异常：", e)
      alert("AI 服务调用失败，可能稍后重试")
    }
  }
}
