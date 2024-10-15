package ra.pj05.service;

import ra.pj05.model.entity.Color;

import java.util.List;

public interface IColorService {
    List<Color> findAllColors();
    Color addColor(String colorName);
    Color saveColor(Color color);
    Color findColorById(Long colorId);
    void deleteColorById(Long colorId);
}
