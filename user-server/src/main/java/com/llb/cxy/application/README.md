
##2.Application：应用层，主要存在应用层服务组合和编排相关的代码。基于微服务内的领域服务或外部微服务的应用服务完成服务的编排和组合，为用户接口层提供各种数据展现支持服务。应用服务和事件代码会放在这一层目录里。
  
  event：事件，主要存放事件相关代码，包括两个子目录：publish和subscribe。前者主要存放事件发布相关代码，后者主要存放事件订阅相关代码（事件处理相关的核心业务逻辑在领域层实现）。
  
  service：应用服务，对多个领域服务或外部应用服务进行封装、编排和组合，对外提供粗粒度的服务。