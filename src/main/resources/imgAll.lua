-- 清除缓存
redis.call('DEL', KEYS[3])

-- 获取 各个缓存id
local m1 = redis.call('SMEMBERS', KEYS[1])
local m2 = redis.call('SMEMBERS', KEYS[2])

-- 将 url库 的元素添加到zset中，分数为1
for _, member in ipairs(m1) do
    redis.call('ZADD', KEYS[3], 1, member)
end

-- 将 file库 的元素添加到zset中，分数为2
for _, member in ipairs(m2) do
    redis.call('ZADD', KEYS[3], 2, member)
end