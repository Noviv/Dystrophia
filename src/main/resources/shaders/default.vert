#version 330 core

layout(location = 0) in vec3 in_Position;

out vec3 pass_Color;

uniform mat4 projection;

void main(void) {
    gl_Position = projection * vec4(in_Position, 1.0);
    if (in_Position.x >= -1.0 && in_Position.x <= 1.0) {
        pass_Color = vec3(0.0, 1.0, 0.0);
    } else {
        pass_Color = vec3(1.0, 0.0, 0.0);
    }
}