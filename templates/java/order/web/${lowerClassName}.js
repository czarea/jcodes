import request from '@/utils/request';

const create = data => request({
  url: '/@{table.restfulUri}s',
  method: 'post',
  data,
});

const update = data => request({
  url: '/@{table.restfulUri}s',
  method: 'put',
  data,
});

const indexURL = '/@{table.restfulUri}s';

const index = params => request({
  url: indexURL,
  method: 'get',
  params,
});

const show = id => request({
  url: `/@{table.restfulUri}s/${id}`,
  method: 'get',
});


const delete@{table.className} = id => request({
  url: `/@{table.restfulUri}s/${id}`,
  method: 'delete',
});

export default {
  create,
  update,
  index,
  show,
  delete@{table.className},
  indexURL,
};
